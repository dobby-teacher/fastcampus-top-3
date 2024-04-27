package com.fastcampus.nextdiscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class NextDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(NextDiscoveryApplication.class, args);
	}

}
