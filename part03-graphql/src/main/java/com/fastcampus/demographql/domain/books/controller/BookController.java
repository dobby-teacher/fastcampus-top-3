package com.fastcampus.demographql.domain.books.controller;

import com.fastcampus.demographql.domain.authors.service.AuthorService;
import com.fastcampus.demographql.domain.books.entity.Book;
import com.fastcampus.demographql.domain.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Controller
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @MutationMapping
    public Book addBook(@Argument String title, @Argument String publisher, @Argument LocalDate publishedDate, @Argument List<Long> authorIds) {
        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setPublisher(publisher);
        newBook.setPublishedDate(publishedDate);
        newBook.setAuthors(new HashSet<>(authorService.findAllById(authorIds)));

        return bookService.saveBook(newBook);
    }

    @QueryMapping
    public Book getBookById(@Argument Long id) {
        return bookService.findById(id)
                .orElseThrow();
    }

    @QueryMapping
    public List<Book> getBooks() {
        return bookService.findAll();
    }

    @QueryMapping
    public List<Book> getBooksByAuthorName(@Argument String authorName) {
        return bookService.findByAuthorName(authorName);
    }

    @MutationMapping
    public Map<String, Boolean> deleteBook(@Argument Long id) {
        bookService.deleteById(id);

        Map<String, Boolean> resultMap = new HashMap<>();
        resultMap.put("success", true);

        return resultMap;
    }

}
