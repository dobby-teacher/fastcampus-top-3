package com.fastcampus.demogrpc.domain.reviews.service;

import com.fastcampus.demogrpc.domain.reviews.entity.Review;
import com.fastcampus.demogrpc.domain.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }


    public Optional<Review> findById(Long id) {
        return reviewRepository.findById(id);
    }

    public List<Review> findByBookId(Long bookId) {
        return reviewRepository.findByBookIdOrderByCreatedDateDesc(bookId);
    }


    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }
}
