package com.fastcampus.nextcourseservice.domain.courses.repository;

import com.fastcampus.nextcourseservice.domain.courses.entity.CourseRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRatingRepository extends JpaRepository<CourseRating, Long> {
    List<CourseRating> findByCourseId(Long courseId);
}
