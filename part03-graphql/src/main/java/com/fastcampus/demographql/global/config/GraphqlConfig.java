package com.fastcampus.demographql.global.config;

import com.fastcampus.demographql.global.directive.AuthenticationDirective;
import graphql.scalars.ExtendedScalars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphqlConfig {
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer(AuthenticationDirective authDirective) {
        return wiringBuilder -> wiringBuilder
                .scalar(ExtendedScalars.Date)
                .scalar(ExtendedScalars.DateTime)
                .scalar(ExtendedScalars.GraphQLLong)
                .directive("auth", authDirective);
    }
}