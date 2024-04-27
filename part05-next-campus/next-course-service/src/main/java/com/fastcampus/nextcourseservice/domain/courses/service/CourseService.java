package com.fastcampus.nextcourseservice.domain.courses.service;

import com.fastcampus.nextcourseservice.domain.courses.entity.Course;
import com.fastcampus.nextcourseservice.domain.courses.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long courseId, Course courseDetails) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id " + courseId));
        course.setTitle(courseDetails.getTitle());
        course.setDescription(courseDetails.getDescription());
        course.setInstructorId(courseDetails.getInstructorId());
        return courseRepository.save(course);
    }

    public Optional<Course> getCourseById(Long courseId) {
        return courseRepository.findById(courseId);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
