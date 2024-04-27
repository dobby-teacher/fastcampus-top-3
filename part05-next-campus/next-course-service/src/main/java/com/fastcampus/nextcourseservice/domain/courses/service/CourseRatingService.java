package com.fastcampus.nextcourseservice.domain.courses.service;

import com.fastcampus.nextcourseservice.domain.courses.entity.Course;
import com.fastcampus.nextcourseservice.domain.courses.entity.CourseRating;
import com.fastcampus.nextcourseservice.domain.courses.repository.CourseRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseRatingService {

    private final CourseRatingRepository ratingRepository;

    @Autowired
    public CourseRatingService(CourseRatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public CourseRating addRatingToCourse(Long courseId, CourseRating rating) {
        rating.setCourse(new Course(courseId)); // Assuming Course constructor accepts courseId
        return ratingRepository.save(rating);
    }

    public CourseRating updateRating(Long ratingId, CourseRating ratingDetails) {
        CourseRating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found with id " + ratingId));
        rating.setRating(ratingDetails.getRating());
        rating.setComment(ratingDetails.getComment());
        return ratingRepository.save(rating);
    }

    public void deleteRating(Long ratingId) {
        ratingRepository.deleteById(ratingId);
    }

    public List<CourseRating> getAllRatingsByCourseId(Long courseId) {
        return ratingRepository.findByCourseId(courseId);
    }

    public Optional<CourseRating> getRatingById(Long ratingId) {
        return ratingRepository.findById(ratingId);
    }
}
