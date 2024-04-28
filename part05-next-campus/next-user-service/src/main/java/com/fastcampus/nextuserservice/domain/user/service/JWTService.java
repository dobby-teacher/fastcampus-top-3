package com.fastcampus.nextuserservice.domain.user.service;

import com.fastcampus.nextuserservice.domain.user.entity.User;
import com.fastcampus.nextuserservice.domain.user.repository.UserRepository;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JWTService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No user found with this email address"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .subject(email)
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

    private Claims parseJwtClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
