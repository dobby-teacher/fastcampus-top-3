package com.fastcampus.nextuserservice.domain.user.controller;

import com.fastcampus.nextuserservice.domain.user.dto.AuthRequest;
import com.fastcampus.nextuserservice.domain.user.dto.TokenRequest;
import com.fastcampus.nextuserservice.domain.user.entity.User;
import com.fastcampus.nextuserservice.domain.user.service.JWTService;
import com.fastcampus.nextuserservice.domain.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "토큰 생성", description = "사용자 인증 정보를 기반으로 JWT 토큰을 생성합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 토큰이 생성됨", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<Map<String, String>> generateToken(
            @Parameter(description = "HTTP 요청 객체", hidden = true)
            HttpServletRequest request,
            @Parameter(description = "인증 요청 객체", required = true)
            @RequestBody AuthRequest authRequest) {
        User existingUser = userService.getUserByEmail(authRequest.getEmail()).orElseThrow();
        String token = jwtService.generateToken(existingUser, authRequest.getPassword());
        String ipAddress = request.getRemoteAddr();

        userService.logUserLogin(existingUser, ipAddress);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    @PostMapping("/validate")
    @Operation(summary = "토큰 유효성 검사", description = "JWT 토큰의 유효성을 검사합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "토큰이 유효함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "토큰이 유효하지 않음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<User> validateToken(
            @Parameter(description = "토큰 요청 객체", required = true)
            @RequestBody TokenRequest tokenRequest) {
        Claims claims = jwtService.parseJwtClaims(tokenRequest.getToken());
        return ResponseEntity.ok(userService.getUserByEmail(claims.getSubject()).orElseThrow());
    }

    @PostMapping("/verify-token")
    @Operation(summary = "토큰 검증", description = "JWT 토큰의 유효성을 검증합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "토큰 검증 결과 반환", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<Map<String, Boolean>> verifyToken(
            @Parameter(description = "토큰 요청 객체", required = true)
            @RequestBody TokenRequest tokenRequest) {
        boolean isValid = jwtService.validateToken(tokenRequest.getToken());
        return ResponseEntity.ok(Collections.singletonMap("isValid", isValid));
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "토큰 갱신", description = "기존 JWT 토큰을 갱신합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 토큰이 갱신됨", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "토큰 갱신 실패"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<Map<String, String>> refreshToken(
            @Parameter(description = "토큰 요청 객체", required = true)
            @RequestBody TokenRequest tokenRequest) {
        String newToken = jwtService.refreshToken(tokenRequest.getToken());
        return ResponseEntity.ok(Collections.singletonMap("token", newToken));
    }
}
