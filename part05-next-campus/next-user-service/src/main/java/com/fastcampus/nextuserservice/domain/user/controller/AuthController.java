package com.fastcampus.nextuserservice.domain.user.controller;

import com.fastcampus.nextuserservice.domain.user.dto.AuthRequest;
import com.fastcampus.nextuserservice.domain.user.dto.TokenRequest;
import com.fastcampus.nextuserservice.domain.user.entity.User;
import com.fastcampus.nextuserservice.domain.user.service.JWTService;
import com.fastcampus.nextuserservice.domain.user.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JWTService jwtService;
    private final UserService userService;

    public AuthController(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/token")
    public ResponseEntity<Map<String, String>> generateToken(HttpServletRequest request, @RequestBody AuthRequest authRequest) {
        User existingUser = userService.getUserByEmail(authRequest.getEmail()).orElseThrow();
        String token = jwtService.generateToken(existingUser, authRequest.getPassword());
        String ipAddress = request.getRemoteAddr();

        userService.logUserLogin(existingUser, ipAddress);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    @PostMapping("/validate")
    public ResponseEntity<User> validateToken(@RequestBody TokenRequest tokenRequest) {
        Claims claims = jwtService.parseJwtClaims(tokenRequest.getToken());
        return ResponseEntity.ok(userService.getUserByEmail(claims.getSubject()).orElseThrow());
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
