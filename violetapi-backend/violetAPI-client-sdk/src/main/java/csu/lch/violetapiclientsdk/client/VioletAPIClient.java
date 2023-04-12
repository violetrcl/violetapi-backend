package csu.lch.violetapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import csu.lch.violetapiclientsdk.utils.SignUtils;

import csu.lch.violetapidubbointerface.entity.User;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * http客户端（生成API签名，发送http请求）
 *
 * @author violetRcl
 */

public class VioletAPIClient {

    // 网关端口号
    private static final String GATEWAY_HOST = "http://localhost:8082";

    /**
     * 调用接口的标识
     */
    private String accessKey;

    /**
     * 密钥（复杂、无规律）
     */
    private String secretKey;

    public VioletAPIClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name){
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.get(GATEWAY_HOST + "/api/name/get", paramMap);
        System.out.println(result);
        return result;
    }

    //Restful
    public String getNameByPost(@RequestBody User user){
        String json = JSONUtil.toJsonStr(user);
        //整体调用逻辑：前端调用服务端接口，服务端接口通过客户端（网关转发路由，经过过滤器）调用api接口
        String result;
        try (HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + "/api/name/user")
                .charset(StandardCharsets.UTF_8)// TODO: 2023/4/3 这里有个中文乱码问题没有解决
                .addHeaders(setHeader(json))
                .body(json)
                .execute()) {
            System.out.println(httpResponse.getStatus());
            result = httpResponse.body();
        }
        System.out.println(result);
        return result;
    }

    /**
     * 生成api签名，设置请求头
     *
     * @param body 用户参数
     * @return 请求头
     */
    public Map<String, String> setHeader(String body){
        Map<String, String> headers = new HashMap<>();
        headers.put("accessKey", accessKey);    //调用接口的标识
        headers.put("body", body);    //用户参数
        headers.put("sign", SignUtils.generateSign(body, secretKey));    //api签名，即加密密钥，用户无法从请求头中获取密钥
        headers.put("nonce", RandomUtil.randomNumbers(4));  //临时数，用随机数生成，防重放
        headers.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));    //时间戳，定期清理临时数，减小存储压力
        return headers;
    }
}
