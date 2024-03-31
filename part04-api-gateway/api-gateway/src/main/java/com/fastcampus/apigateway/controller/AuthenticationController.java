package com.fastcampus.apigateway.controller;

import com.fastcampus.apigateway.global.security.JwtUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AuthenticationController {

    private final ReactiveAuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthenticationController(ReactiveAuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());

        return authenticationManager.authenticate(authentication)
                .map(auth -> {
                    String token = jwtUtil.generateToken(auth.getName());
                    return ResponseEntity.ok(new AuthResponse(token));
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @Getter
    @Setter
    public static class AuthRequest {
        private String username;
        private String password;
    }

    public record AuthResponse(String token) {
    }
}
