package com.fastcampus.restapidemo.domain.reviews.controller;

import com.fastcampus.restapidemo.domain.reviews.entity.Review;
import com.fastcampus.restapidemo.domain.reviews.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{bookId}/reviews")
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        Review savedReview = reviewService.saveReview(review);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @GetMapping("/{bookId}/reviews")
    public ResponseEntity<List<Review>> getReviewsByBookId(@PathVariable Long bookId) {
        List<Review> reviews = reviewService.findByBookId(bookId);
        if (reviews.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long bookId, @PathVariable Long id) {
        reviewService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
