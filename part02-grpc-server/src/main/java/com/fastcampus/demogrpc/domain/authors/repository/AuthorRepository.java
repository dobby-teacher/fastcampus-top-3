package com.fastcampus.demogrpc.domain.authors.repository;

import com.fastcampus.demogrpc.domain.authors.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
