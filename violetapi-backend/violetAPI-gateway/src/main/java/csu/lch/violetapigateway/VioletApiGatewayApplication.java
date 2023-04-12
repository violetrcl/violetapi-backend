package csu.lch.violetapigateway;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class VioletApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(VioletApiGatewayApplication.class, args);
	}
}
