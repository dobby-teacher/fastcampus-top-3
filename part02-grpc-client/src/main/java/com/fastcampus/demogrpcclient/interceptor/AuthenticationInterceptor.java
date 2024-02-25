package com.fastcampus.demogrpcclient.interceptor;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;

@Slf4j
public class AuthenticationInterceptor implements ClientInterceptor {
    private static final String ID = "fast";
    private static final String PASSWORD = "campus";
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                // authentication
                log.info("## Authentication Interceptor");
                String credentials = ID + ":" + PASSWORD;
                String basicAuth = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
                headers.put(Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER), basicAuth);
                super.start(responseListener, headers);
            }
        };
    }
}
