package com.fastcampus.restapidemo.domain.authors.entity;

import com.fastcampus.restapidemo.domain.authors.request.AuthorRequest;
import com.fastcampus.restapidemo.domain.books.entity.Book;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "authors")
    @JsonBackReference
    private Set<Book> books = new HashSet<>();

    public static Author of(AuthorRequest request) {
        Author author = new Author();
        author.setName(request.getName());

        return author;
    }
}
