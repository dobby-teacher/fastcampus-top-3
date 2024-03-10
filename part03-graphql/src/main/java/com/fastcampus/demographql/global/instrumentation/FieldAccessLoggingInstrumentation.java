package com.fastcampus.demographql.global.instrumentation;

import graphql.execution.instrumentation.Instrumentation;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.InstrumentationState;
import graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLOutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class FieldAccessLoggingInstrumentation implements Instrumentation {

    private static final Logger log = LoggerFactory.getLogger(FieldAccessLoggingInstrumentation.class);

    @Override
    public InstrumentationContext<Object> beginFieldFetch(InstrumentationFieldFetchParameters parameters, InstrumentationState state) {
        DataFetchingEnvironment env = parameters.getEnvironment();
        // 필드 이름과 부모 타입을 가져옵니다.
        String fieldName = env.getField().getName();
        GraphQLOutputType parentTypeName = env.getExecutionStepInfo().getParent().getType();

        return new InstrumentationContext<>() {
            @Override
            public void onDispatched(CompletableFuture<Object> result) {
                // 필드 가져오기가 시작될 때 로그를 남깁니다.
                log.info("Fetching field: {} of type: {}", fieldName, parentTypeName);
            }
            @Override
            public void onCompleted(Object result, Throwable t) {
                // 필드 가져오기가 완료될 때의 추가적인 로그를 남길 수 있습니다.
                log.info("Completed fetching field: {}", fieldName);
            }
        };
    }
}
