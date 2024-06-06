package com.fastcampus.nextuserservice.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/auth/error-test")
    public ResponseEntity<String> test() {
        throw new RuntimeException("Test");
    }

    @GetMapping("/auth/delayed-response")
    public ResponseEntity<String> delayedResponse() {
        try {
            Thread.sleep(3000); // 3초 지연
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(500).body("Thread was interrupted");
        }
        return ResponseEntity.ok("Response after 3 seconds");
    }
}
