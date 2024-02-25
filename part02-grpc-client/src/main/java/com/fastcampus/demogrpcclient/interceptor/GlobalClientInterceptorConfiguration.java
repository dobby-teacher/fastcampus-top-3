package com.fastcampus.demogrpcclient.interceptor;

import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalClientInterceptorConfiguration {

    @GrpcGlobalClientInterceptor
    AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @GrpcGlobalClientInterceptor
    LoggingGrpcInterceptor loggingGrpcInterceptor() {
        return new LoggingGrpcInterceptor();
    }
}
