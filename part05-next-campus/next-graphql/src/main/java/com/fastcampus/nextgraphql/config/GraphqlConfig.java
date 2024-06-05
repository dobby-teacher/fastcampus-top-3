package com.fastcampus.nextgraphql.config;

import com.fastcampus.nextgraphql.directive.AuthenticationDirective;
import com.fastcampus.nextgraphql.directive.AuthorizationDirective;
import graphql.analysis.FieldComplexityCalculator;
import graphql.analysis.FieldComplexityEnvironment;
import graphql.analysis.MaxQueryComplexityInstrumentation;
import graphql.analysis.MaxQueryDepthInstrumentation;
import graphql.execution.instrumentation.ChainedInstrumentation;
import graphql.execution.instrumentation.Instrumentation;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLFieldDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class GraphqlConfig {
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer(AuthenticationDirective authenticationDirective, AuthorizationDirective authorizationDirective) {
        return wiringBuilder -> wiringBuilder
                .scalar(ExtendedScalars.Date)
                .scalar(ExtendedScalars.DateTime)
                .scalar(ExtendedScalars.GraphQLLong)
                .directive("authenticate", authenticationDirective)
                .directive("authorize", authorizationDirective);

    }

    @Bean
    public Instrumentation maxQueryComplexityInstrumentation() {
        MaxQueryComplexityInstrumentation complexityInstrumentation = new MaxQueryComplexityInstrumentation(100, new CustomFieldComplexityCalculator());
        return new ChainedInstrumentation(List.of(complexityInstrumentation));
    }

    @Bean
    public Instrumentation maxQueryDepthInstrumentation() {
        return new MaxQueryDepthInstrumentation(20);
    }

    @Slf4j
    public static class CustomFieldComplexityCalculator implements FieldComplexityCalculator {
        @Override
        public int calculate(FieldComplexityEnvironment environment, int childComplexity) {
            GraphQLFieldDefinition fieldDefinition = environment.getFieldDefinition();
            String fieldName = fieldDefinition.getName();
            log.info("fieldName: {}, childComplexity : {}", fieldName, childComplexity);
//            if ("expensiveField".equals(fieldName)) {
//                return childComplexity + 10; // 더 복잡한 필드에 더 높은 점수 부여
//            }

            return childComplexity + 1;
        }
    }
}