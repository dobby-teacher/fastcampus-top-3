package com.fastcampus.demographql.domain.authors.repository;

import com.fastcampus.demographql.domain.authors.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
