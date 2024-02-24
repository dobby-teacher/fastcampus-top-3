package com.fastcampus.restapidemo.domain.authors.repository;

import com.fastcampus.restapidemo.domain.authors.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
