package com.fastcampus.demographql.domain.authors.service;

import com.fastcampus.demographql.domain.authors.entity.Author;
import com.fastcampus.demographql.domain.authors.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    public List<Author> findAllById(List<Long> ids) {
        return authorRepository.findAllById(ids);
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }
}
