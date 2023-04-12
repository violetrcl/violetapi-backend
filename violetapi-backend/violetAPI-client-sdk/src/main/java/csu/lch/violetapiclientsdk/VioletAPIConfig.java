package csu.lch.violetapiclientsdk;

import csu.lch.violetapiclientsdk.client.VioletAPIClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration  //声明为配置类，相当于bean.xml
@ComponentScan  //组件扫描
@ConfigurationProperties("violetapi.client")    //从配置文件application.yml中读取属性，传入该配置类，可添加前缀

public class VioletAPIConfig {

    private String accessKey;

    private String secretKey;

    @Bean  //springboot启动时自动创建一个VioletAPIClient对象
    public VioletAPIClient getVioletAPIClient(){
        return new VioletAPIClient(accessKey, secretKey);
    }
}
