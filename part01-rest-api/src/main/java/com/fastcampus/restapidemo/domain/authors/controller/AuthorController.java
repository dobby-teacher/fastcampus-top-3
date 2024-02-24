package com.fastcampus.restapidemo.domain.authors.controller;

import com.fastcampus.restapidemo.domain.authors.entity.Author;
import com.fastcampus.restapidemo.domain.authors.request.AuthorRequest;
import com.fastcampus.restapidemo.domain.authors.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<EntityModel<Author>> addAuthor(@RequestBody AuthorRequest author) {
        Author savedAuthor = authorService.saveAuthor(Author.of(author));
        final EntityModel<Author> entityModel = EntityModel.of(savedAuthor);
        entityModel.add(linkTo(methodOn(this.getClass()).getAllAuthors()).withRel("all-authors"));
        entityModel.add(linkTo(methodOn(this.getClass()).getAuthorById(savedAuthor.getId())).withRel("author-by-id"));

        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        return authorService.findById(id)
                .map(author -> new ResponseEntity<>(author, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.findAll();
        if (authors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }
}
