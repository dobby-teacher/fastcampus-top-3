package com.fastcampus.demographql.global.directive;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationDirective implements SchemaDirectiveWiring {

    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
        GraphQLFieldDefinition fieldDefinition = environment.getFieldDefinition();
        GraphQLObjectType parentType = (GraphQLObjectType) environment.getFieldsContainer();

        // 원래의 DataFetcher를 가져옵니다.
        DataFetcher<?> originalDataFetcher = environment.getCodeRegistry().getDataFetcher(parentType, fieldDefinition);

        // 인증 검사를 수행하는 새로운 DataFetcher를 생성합니다.
        DataFetcher<?> authDataFetcher = (DataFetchingEnvironment dataFetchingEnvironment) -> {
            // 현재 인증 정보를 확인합니다.
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // 사용자가 인증되었는지 확인합니다.
            if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
                // 사용자가 인증되지 않았다면 예외를 던집니다.
                throw new IllegalAccessException("Access Denied - Authentication required");
            }

            // 사용자가 인증되었다면 원래의 DataFetcher로 데이터를 가져옵니다.
            return originalDataFetcher.get(dataFetchingEnvironment);
        };

        // 변경된 DataFetcher를 등록합니다.
        environment.getCodeRegistry().dataFetcher(parentType, fieldDefinition, authDataFetcher);

        return fieldDefinition;
    }

}
