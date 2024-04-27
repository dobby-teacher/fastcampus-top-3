package com.fastcampus.nextcourseservice.domain.courses.controller;

import com.fastcampus.nextcourseservice.domain.courses.entity.Course;
import com.fastcampus.nextcourseservice.domain.courses.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course newCourse = courseService.saveCourse(course);
        return ResponseEntity.ok(newCourse);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long courseId, @RequestBody Course course) {
        Course updatedCourse = courseService.updateCourse(courseId, course);
        return ResponseEntity.ok(updatedCourse);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long courseId) {
        Course course = courseService.getCourseById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id " + courseId));
        return ResponseEntity.ok(course);
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }
}
