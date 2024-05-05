package com.fastcampus.nextplaybackservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NextPlaybackServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NextPlaybackServiceApplication.class, args);
	}

}
