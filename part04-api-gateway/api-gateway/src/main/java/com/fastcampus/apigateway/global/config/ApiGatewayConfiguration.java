package com.fastcampus.apigateway.global.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        // http://localhost:8080/fastcampus/search/recommended
        return builder.routes()
                .route(r -> r
                        .path("/fastcampus/**")
                        .filters(f -> f.rewritePath("/fastcampus/(?<segment>.*)", "/.api/www/${segment}"))
                        .uri("https://fastcampus.co.kr/")
                )
                .build();
    }
}
