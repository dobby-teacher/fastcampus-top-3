package com.fastcampus.nextuserservice.domain.user.service;

import com.fastcampus.nextuserservice.domain.user.entity.User;
import com.fastcampus.nextuserservice.domain.user.entity.UserLoginHistory;
import com.fastcampus.nextuserservice.domain.user.repository.UserLoginHistoryRepository;
import com.fastcampus.nextuserservice.domain.user.repository.UserRepository;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@Slf4j
public class JWTService {

    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secretKey;

    public JWTService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String generateToken(User existingUser, String requestPassword) {
        if (!passwordEncoder.matches(requestPassword, existingUser.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .subject(existingUser.getEmail())
                .issuedAt(new Date(currentTimeMillis))
                .expiration(new Date(currentTimeMillis + 3600000)) // Token expires in 1 hour
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            parseJwtClaims(token);
            return true;
        } catch (Exception e) {
            log.error("validateToken error : ", e);
            return false;
        }
    }

    public String refreshToken(String token) {
        Claims claims = parseJwtClaims(token);
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .subject(claims.getSubject())
                .issuedAt(new Date(currentTimeMillis))
                .expiration(new Date(currentTimeMillis + 3600000)) // Token expires in 1 hour
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public Claims parseJwtClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
