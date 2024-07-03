package com.neoflex.prokhorov.web_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.neoflex.prokhorov"})
public class WebGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebGatewayApplication.class, args);
	}

}
