package csu.lch.violetapigateway;

import csu.lch.violetapiclientsdk.utils.SignUtils;

import csu.lch.violetapidubbointerface.dubboserviceinterface.DubboInterfaceInfoService;
import csu.lch.violetapidubbointerface.dubboserviceinterface.DubboUserInterfaceInfoService;
import csu.lch.violetapidubbointerface.dubboserviceinterface.DubboUserService;
import csu.lch.violetapidubbointerface.entity.InterfaceInfo;
import csu.lch.violetapidubbointerface.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Spring Cloud Gateway全局过滤器
 *
 * @author violetRcl
 */

@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    private DubboUserService dubboUserService;

    @DubboReference
    private DubboUserInterfaceInfoService dubboUserInterfaceInfoService;

    @DubboReference
    private DubboInterfaceInfoService dubboInterfaceInfoService;

    /**
     * host
     */
    private static final String INTERFACE_HOST = "http://localhost:8123";

    /**
     * 白名单列表
     */
    private static final List<String> IP_WHITE_LIST = Collections.singletonList("127.0.0.1");

    /**
     * 全局过滤
     *
     * @param exchange  路由交换器
     * @param chain    过滤链（责任链模式）
     * @return  组合过滤器
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        // 1、请求日志
        String path = INTERFACE_HOST + request.getPath().value();
        String method = Objects.requireNonNull(request.getMethod()).toString();
        String sourceAddress = Objects.requireNonNull(request.getLocalAddress()).getHostString();
        log.info("请求唯一标识：" + request.getId());
        log.info("请求路径：" + path);
        // TODO: 2023/4/9 : 思路：网关启动时，获取所有的接口信息，维护到内存的hashmap中；有请求时，根据请求的url路径或者其他参数（比如host请求头）来判断应该转发到哪台服务器、以及用于校验接口是否存在
        log.info("请求方法：" + method);
        log.info("请求参数：" + request.getQueryParams());
        log.info("请求来源地址：" + sourceAddress);

        // 2、黑白名单 - 访问控制
        if (!IP_WHITE_LIST.contains(sourceAddress)){
            return handleNoAuth(response);
        }

        // 3、用户鉴权（ak, sk）
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String sign = headers.getFirst("sign");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String body = headers.getFirst("body");
        // 获取调用用户
        User invokeUser = dubboUserService.getInvokeUser(accessKey);
        if (invokeUser == null) {
            return handleNoAuth(response);
        }
        // 判断临时数是否超出范围
        // TODO: 2023/4/3 临时数可以用HashMap或Redis存储
        assert nonce != null;
        if (Long.parseLong(nonce) >= 10000L){
            return handleNoAuth(response);
        }
        //临时数的时间不能超过十分钟(600s)，否则失效
        assert timestamp != null;
        if ((System.currentTimeMillis() / 1000 - Long.parseLong(timestamp)) > 600){
            return handleNoAuth(response);
        }
        // 验证API签名（从数据库中取出secretKey并加密生成API签名）
        String secretKey = invokeUser.getSecretKey();
        String serverSign = SignUtils.generateSign(body, secretKey);
        assert sign != null;
        if (!sign.equals(serverSign)){
            return handleNoAuth(response);
        }
        
        // 4、验证以及请求方法是否匹配（针对服务器内部），并获取被调用的接口的信息
        InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo = dubboInterfaceInfoService.getInterfaceInfo(path, method);
        } catch (Exception e) {
            log.error("获取接口异常" + e);
        }
        if (interfaceInfo == null) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return response.setComplete();
        }
        // TODO: 2023/4/9 是否还有调用次数

//        // 5、请求转发，调用接口
//        Mono<Void> filter = chain.filter(exchange);// TODO: 2023/4/6 filter方法看不懂，Mono是异步编程

        // 5、请求转发，调用接口，处理响应
        return handleResponse(exchange, chain, interfaceInfo.getId(), invokeUser.getId());
    }

    /**
     * 处理响应
     *
     * @param exchange  路由交换器
     * @param chain    过滤链（责任链模式）
     * @return  响应处理
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long interfaceInfoId, long userId) {
        try {
            //从交换器中拿到响应对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓存数据的工厂，拿到缓存数据
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();

            if (statusCode == HttpStatus.OK) {
                // 装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        // 对象是响应式的
                        if (body instanceof Flux) {
                            // 拿到真正的body
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值里写数据
                            // 拼接字符串
                            return super.writeWith(
                                    fluxBody.map(dataBuffer -> {
                                        // 调用成功后，接口调用次数 + 1，剩余次数 - 1
                                        try {
                                            dubboUserInterfaceInfoService.invokeCount(interfaceInfoId, userId);
                                        } catch (Exception e) {
                                            log.error("接口调用次数统计异常" + e);
                                        }
                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        DataBufferUtils.release(dataBuffer);//释放掉内存
                                        // 构建日志
                                        StringBuilder sb2 = new StringBuilder(200);
                                        List<Object> rspArgs = new ArrayList<>();
                                        rspArgs.add(originalResponse.getStatusCode());
                                        String data = new String(content, StandardCharsets.UTF_8); //data
                                        sb2.append(data);
                                        // 打印日志
                                        log.info("响应结果：" + data);
                                        return bufferFactory.wrap(content);
                                    }));
                        } else {
                            // 8. 调用失败，返回一个规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange); // 降级处理返回数据
        } catch (Exception e) {
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }

    /**
     * 无权限处理
     *
     * @param serverHttpResponse  响应
     * @return  响应结束
     */
    public Mono<Void> handleNoAuth(ServerHttpResponse serverHttpResponse) {
        serverHttpResponse.setStatusCode(HttpStatus.FORBIDDEN);
        return serverHttpResponse.setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}