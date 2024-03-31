package com.fastcampus.bookapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PaymentController {
    @GetMapping("/v1/payments/{id}")
    public ResponseEntity getPaymentsInfo(@PathVariable("id") long paymentId) {
        return new ResponseEntity<>(
                Map.of(
                        "paymentId", paymentId,
                        "paymentInfo", Map.of("paymentMethod", "CARD"),
                        "timestamp", System.currentTimeMillis()), HttpStatus.OK
        );
    }
}
