package com.fastcampus.restapidemo.domain.books.controller;

import com.fastcampus.restapidemo.domain.books.entity.Book;
import com.fastcampus.restapidemo.domain.books.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Tag(name = "books", description = "책 관련 API")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @Operation(summary = "새로운 책을 추가한다")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "하나의 책을 조회한다",
        responses = {
                @ApiResponse(responseCode = "200", description = "정상적으로 조회 완료"),
                @ApiResponse(responseCode = "404", description = "존재하지 않는 책을 조회하는 경우")
        }
    )
    public ResponseEntity<Book> getBookById(@Parameter(description = "책 ID") @PathVariable Long id) {
        return bookService.findById(id)
                .map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    @Operation(summary = "전체의 책을 조회한다")
    public ResponseEntity<List<Book>> getBooks(@RequestParam(required = false) String author) {
        if (author != null && !author.isEmpty()) {
            return new ResponseEntity<>(bookService.findByAuthorName(author), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(bookService.findAll(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "하나의 책을 삭제한다")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
