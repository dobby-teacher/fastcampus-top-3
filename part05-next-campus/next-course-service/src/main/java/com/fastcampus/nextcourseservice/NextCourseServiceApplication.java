package com.fastcampus.nextcourseservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NextCourseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NextCourseServiceApplication.class, args);
	}

}
