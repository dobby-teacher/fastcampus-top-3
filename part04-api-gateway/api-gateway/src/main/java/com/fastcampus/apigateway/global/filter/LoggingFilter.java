package com.fastcampus.apigateway.global.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Objects;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // 요청 로깅
            logRequest(exchange);
            
            // 응답 로깅은 응답 처리 후에 실행
            return chain.filter(exchange).then(Mono.fromRunnable(() -> logResponse(exchange)));
        };
    }

    private void logRequest(ServerWebExchange exchange) {
        String requestPath = exchange.getRequest().getPath().toString();
        String httpMethod = exchange.getRequest().getMethod().name();
        log.info("Incoming request {} {} {}", new Date(), httpMethod, requestPath);
    }

    private void logResponse(ServerWebExchange exchange) {
        Integer statusCode = Objects.requireNonNull(exchange.getResponse().getStatusCode()).value();
        log.info("Response with status code {}", statusCode);
    }

    public static class Config {
        // 필터 구성을 위한 설정을 추가
    }
}
