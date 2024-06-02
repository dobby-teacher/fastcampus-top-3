package com.fastcampus.nextgraphql.directive;

import com.fastcampus.nextgraphql.exception.UnauthorizedException;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
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
            String userId = dataFetchingEnvironment.getGraphQlContext().get("X-USER-ID");
            if (userId == null || userId.trim().isEmpty()) {
                throw new UnauthorizedException("Unauthorized: Missing X-USER-ID header");
            }
            return originalDataFetcher.get(dataFetchingEnvironment);
        };

        // 변경된 DataFetcher를 등록합니다.
        environment.getCodeRegistry().dataFetcher(parentType, fieldDefinition, authDataFetcher);

        return fieldDefinition;
    }

}