package com.fastcampus.nextcourseservice.domain.courses.repository;

import com.fastcampus.nextcourseservice.domain.courses.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
