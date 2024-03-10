package com.fastcampus.demographql.global.instrumentation;

import graphql.ExecutionResult;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.InstrumentationState;
import graphql.execution.instrumentation.SimplePerformantInstrumentation;
import graphql.execution.instrumentation.parameters.InstrumentationExecuteOperationParameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class PerformanceMonitoringInstrumentation extends SimplePerformantInstrumentation {
    private final Clock clock = Clock.systemUTC();

    @Override
    public InstrumentationContext<ExecutionResult> beginExecuteOperation(InstrumentationExecuteOperationParameters parameters, InstrumentationState state) {
        Instant start = clock.instant();
        return new InstrumentationContext<>() {
            @Override
            public void onDispatched(CompletableFuture<ExecutionResult> result) {
                // No action taken on dispatch
            }

            @Override
            public void onCompleted(ExecutionResult result, Throwable t) {
                Instant end = clock.instant();
                log.info("Operation {} took {} ms", parameters.getExecutionContext().getExecutionInput().getExecutionId(), Duration.between(start, end).toMillis());
            }
        };
    }
}
