package com.fastcampus.demogrpc.global.interceptor;


import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@Slf4j
public class BasicAuthInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        log.info("## BasicAuthInterceptor");
        String username = "fast";
        String password = "campus";

        String authorization = headers.get(Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER));
        if (authorization == null || !authorization.startsWith("Basic ")) {
            call.close(Status.UNAUTHENTICATED.withDescription("Missing or invalid Authorization header"), new Metadata());
            return new ServerCall.Listener<>() {};
        }

        String credentials = authorization.substring(6);
        // fast:campus -> base64
        String[] usernameAndPassword = new String(Base64.getDecoder().decode(credentials)).split(":");
        String providedUsername = usernameAndPassword[0];
        String providedPassword = usernameAndPassword[1];

        if (!providedUsername.equals(username) || !providedPassword.equals(password)) {
            call.close(Status.PERMISSION_DENIED.withDescription("Invalid username or password"), new Metadata());
            return new ServerCall.Listener<>() {};
        }

        return next.startCall(call, headers);
    }
}
