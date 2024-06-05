package com.fastcampus.nextgateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final WebClient webClient;

    public AuthenticationFilter() {
        super(Config.class);
        this.webClient = WebClient.builder()
                                  .baseUrl("http://next-user-service/auth")
                                  .build();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                return validateToken(token)
                        .flatMap(userId -> proceedWithUserId(userId, exchange, chain))
                        .switchIfEmpty(chain.filter(exchange)); // 토큰 검증 실패 시 계속 진행할 수 있도록 처리
            }
            return chain.filter(exchange); // 헤더가 없거나 Bearer 토큰이 아닐 때 요청 계속
        };
    }

    private Mono<Long> validateToken(String token) {
        return webClient.post()
                        .uri("/validate")
                        .bodyValue("{\"token\":\"" + token + "\"}")
                        .retrieve()
                        .bodyToMono(Map.class)
                        .map(response -> (Long) response.get("id"));
    }

    private Mono<Void> proceedWithUserId(Long userId, ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getRequest().mutate().header("X-USER-ID", String.valueOf(userId));
        return chain.filter(exchange);
    }

    public static class Config {
        // 필터 구성을 위한 설정 클래스
    }
}
