package com.fastcampus.nextgraphql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NextGraphqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(NextGraphqlApplication.class, args);
	}

}
