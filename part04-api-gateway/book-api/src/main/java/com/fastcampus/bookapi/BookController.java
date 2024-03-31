package com.fastcampus.bookapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class BookController {
    @GetMapping("/v1/books/{id}")
    public ResponseEntity getPaymentsInfo(@PathVariable("id") long bookId) {
        return new ResponseEntity<>(
                Map.of(
                        "bookId", bookId,
                        "bookInfo", Map.of("bookName", "testbook"),
                        "timestamp", System.currentTimeMillis()), HttpStatus.OK
        );
    }
}
