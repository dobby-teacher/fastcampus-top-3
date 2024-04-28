package com.fastcampus.nextuserservice.domain.user.controller;

import com.fastcampus.nextuserservice.domain.user.dto.AuthRequest;
import com.fastcampus.nextuserservice.domain.user.dto.TokenRequest;
import com.fastcampus.nextuserservice.domain.user.service.JWTService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JWTService jwtService;

    public AuthController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/token")
    public ResponseEntity<Map<String, String>> generateToken(@RequestBody AuthRequest authRequest) {
        String token = jwtService.generateToken(authRequest.getEmail(), authRequest.getPassword());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    @PostMapping("/verify-token")
    public ResponseEntity<Map<String, Boolean>> verifyToken(@RequestBody TokenRequest tokenRequest) {
        boolean isValid = jwtService.validateToken(tokenRequest.getToken());
        return ResponseEntity.ok(Collections.singletonMap("isValid", isValid));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody TokenRequest tokenRequest) {
        String newToken = jwtService.refreshToken(tokenRequest.getToken());
        return ResponseEntity.ok(Collections.singletonMap("token", newToken));
    }
}
