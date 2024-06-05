package com.fastcampus.nextgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NextGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(NextGatewayApplication.class, args);
	}

}
