package com.fastcampus.nextgateway.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Order(-1)
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        if (ex instanceof ResponseStatusException) {
            response.setStatusCode(((ResponseStatusException) ex).getStatusCode());
        }

        return response
                .writeWith(Mono.fromSupplier(() -> {
                    DataBufferFactory bufferFactory = response.bufferFactory();
                    try {
                        GlobalErrorResponse globalErrorResponse = GlobalErrorResponse.defaultBuild(ex.getMessage());
                        byte[] errorResponse = objectMapper.writeValueAsBytes(globalErrorResponse);
                        return bufferFactory.wrap(errorResponse);
                    } catch (Exception e) {
                        log.error("error", e);
                        return bufferFactory.wrap(new byte[0]);
                    }
                }));
    }

    @Getter
    public static class GlobalErrorResponse {
        private final String errorMessage;
        private final LocalDateTime localDateTime;
        private final Map<String, Object> additionalInfos = new HashMap<>();

        public GlobalErrorResponse(String errorMessage, LocalDateTime localDateTime) {
            this.errorMessage = errorMessage;
            this.localDateTime = localDateTime;
        }

        public static GlobalErrorResponse defaultBuild(String errorMessage) {
            return new GlobalErrorResponse(errorMessage, LocalDateTime.now());
        }

    }
}