package com.fastcampus.demogrpcclient;

import bookstore.Bookstore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookGrpcClient bookGrpcClient;

    @Autowired
    public BookController(BookGrpcClient bookGrpcClient) {
        this.bookGrpcClient = bookGrpcClient;
    }

    @GetMapping
    public ResponseEntity getBookList() throws IOException {
        return ResponseEntity.ok().body(JsonConverter.toJsonList(bookGrpcClient.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity getBookById(@PathVariable Long id) throws IOException {
        Bookstore.Book book = bookGrpcClient.findById(id);
        return ResponseEntity.ok().body(JsonConverter.toJson(book));
    }
}
