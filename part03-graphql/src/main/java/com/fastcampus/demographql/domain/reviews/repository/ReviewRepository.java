package com.fastcampus.demographql.domain.reviews.repository;

import com.fastcampus.demographql.domain.reviews.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBookIdOrderByCreatedDateDesc(Long bookId);
}
