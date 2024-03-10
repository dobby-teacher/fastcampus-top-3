package com.fastcampus.demographql.domain.reviews.entity;

import com.fastcampus.demographql.domain.books.entity.Book;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    @JsonBackReference
    private Book book;

    @Column(nullable = false)
    private String content;

    private float rating;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime createdDate;
}
