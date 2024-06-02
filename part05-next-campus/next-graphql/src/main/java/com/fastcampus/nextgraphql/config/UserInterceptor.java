package com.fastcampus.nextgraphql.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.server.*;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Configuration
public class UserInterceptor implements WebGraphQlInterceptor {

    private static final Logger log = LoggerFactory.getLogger(UserInterceptor.class);

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        log.info("GraphQL Request: {}", request.getDocument());
        log.info("Headers: {}", request.getHeaders());

        String userId = request.getHeaders().getFirst("X-USER-ID");
        String userRole = request.getHeaders().getFirst("X-USER-ROLE");

        request.configureExecutionInput((executionInput, executionInputBuilder) -> {
            executionInput.getGraphQLContext().put("X-USER-ID", Objects.requireNonNullElse(userId, "-1"));
            executionInput.getGraphQLContext().put("X-USER-ROLE", Objects.requireNonNullElse(userRole, "user"));

            return executionInput;
        });


        return chain.next(request);
    }
}
