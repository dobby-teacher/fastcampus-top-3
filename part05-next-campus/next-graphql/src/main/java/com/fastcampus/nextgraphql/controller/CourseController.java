package com.fastcampus.nextgraphql.controller;

import com.fastcampus.nextgraphql.model.CourseRating;
import com.fastcampus.nextgraphql.model.CourseSession;
import com.fastcampus.nextgraphql.service.DummyCourseService;
import com.fastcampus.nextgraphql.model.Course;
import com.fastcampus.nextgraphql.model.CourseSessionFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CourseController {
    private final DummyCourseService courseService;

    @Autowired
    public CourseController(DummyCourseService courseService) {
        this.courseService = courseService;
    }

    @QueryMapping
    public List<Course> listCourses() {
        return courseService.findAllCourses();
    }

    @QueryMapping
    public Course getCourse(@Argument Long courseId) {
        return courseService.findCourseById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @QueryMapping
    public List<CourseSession> listCourseSessions(@Argument Long courseId) {
        return courseService.findAllSessionsByCourseId(courseId);
    }

    @QueryMapping
    public List<CourseSessionFile> getCourseSessionFiles(@Argument Long courseSessionId) {
        return courseService.findAllSessionFilesBySessionId(courseSessionId);
    }

    @MutationMapping
    public Course createCourse(@Argument String title, @Argument String description, @Argument Long instructorId) {
        return courseService.createCourse(title, description, instructorId);
    }

    @MutationMapping
    public Course updateCourse(@Argument Long id, @Argument String title, @Argument String description) {
        return courseService.updateCourse(id, title, description);
    }

    @MutationMapping
    public CourseSession addCourseSession(@Argument Long courseId, @Argument String title) {
        return courseService.addSessionToCourse(courseId, title);
    }

    @MutationMapping
    public CourseRating rateCourse(@Argument Long userId, @Argument Long courseId, @Argument Integer rating, @Argument String comment) {
        return courseService.addRatingToCourse(userId, courseId, rating, comment);
    }
}
