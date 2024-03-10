package com.fastcampus.demographql.domain.authors.controller;

import com.fastcampus.demographql.domain.authors.entity.Author;
import com.fastcampus.demographql.domain.authors.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @QueryMapping
    public List<Author> getAllAuthors() {
        return authorService.findAll();
    }
    @MutationMapping
    public Author addAuthor(@Argument String authorName) {
        Author author = new Author();
        author.setName(authorName);

        return authorService.saveAuthor(author);
    }

    @QueryMapping
    public Author getAuthorById(@Argument Long id) {
        return authorService.findById(id).orElseThrow();
    }
}
