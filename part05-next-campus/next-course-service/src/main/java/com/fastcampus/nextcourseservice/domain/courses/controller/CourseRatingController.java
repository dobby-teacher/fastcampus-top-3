package com.fastcampus.nextcourseservice.domain.courses.controller;

import com.fastcampus.nextcourseservice.domain.courses.entity.CourseRating;
import com.fastcampus.nextcourseservice.domain.courses.service.CourseRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses/{courseId}/ratings")
public class CourseRatingController {

    private final CourseRatingService ratingService;

    @Autowired
    public CourseRatingController(CourseRatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<CourseRating> addRating(@PathVariable Long courseId, @RequestBody CourseRating rating) {
        CourseRating newRating = ratingService.addRatingToCourse(courseId, rating);
        return ResponseEntity.ok(newRating);
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<CourseRating> updateRating(@PathVariable Long ratingId, @RequestBody CourseRating rating) {
        CourseRating updatedRating = ratingService.updateRating(ratingId, rating);
        return ResponseEntity.ok(updatedRating);
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long ratingId) {
        ratingService.deleteRating(ratingId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CourseRating>> getAllRatings(@PathVariable Long courseId) {
        List<CourseRating> ratings = ratingService.getAllRatingsByCourseId(courseId);
        return ResponseEntity.ok(ratings);
    }
}
