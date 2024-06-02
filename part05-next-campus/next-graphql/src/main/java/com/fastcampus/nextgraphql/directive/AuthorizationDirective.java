package com.fastcampus.nextgraphql.directive;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Component
public class AuthorizationDirective implements SchemaDirectiveWiring {
    private final RolePermissionService rolePermissionService;
    public AuthorizationDirective(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
        String requiredRole = environment.getAppliedDirective("authorize").getArgument("role").getValue();
        String requiredPermission = environment.getAppliedDirective("authorize").getArgument("permission").getValue();

        GraphQLFieldDefinition fieldDefinition = environment.getFieldDefinition();
        GraphQLObjectType parentType = (GraphQLObjectType) environment.getFieldsContainer();

        DataFetcher<?> originalDataFetcher = environment.getCodeRegistry().getDataFetcher(parentType, fieldDefinition);
        DataFetcher<?> authDataFetcher = (DataFetchingEnvironment dataFetchingEnvironment) -> {
            if (!rolePermissionService.checkPermission(requiredRole, requiredPermission, dataFetchingEnvironment)) {
                throw new AccessDeniedException("You do not have permission to perform this action.");
            }

            return originalDataFetcher.get(dataFetchingEnvironment);
        };

        // 변경된 DataFetcher를 등록합니다.
        environment.getCodeRegistry().dataFetcher(parentType, fieldDefinition, authDataFetcher);

        return fieldDefinition;
    }

}