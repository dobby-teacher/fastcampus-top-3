package com.fastcampus.restapidemo.domain.books.repository;

import com.fastcampus.restapidemo.domain.books.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorsName(String name);

    List<Book> findByTitleContaining(String keyword);
}
