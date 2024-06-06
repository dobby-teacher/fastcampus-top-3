package com.fastcampus.nextgateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/fallback")
public class FallbackController {
    @GetMapping("/authFailure")
    public Mono<Map<String, Object>> authFailure() {
        return Mono.just(Map.of("status", "down"));
    }
}