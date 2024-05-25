package com.fastcampus.nextgraphql.resolver;

import com.fastcampus.nextgraphql.model.Course;
import com.fastcampus.nextgraphql.model.CourseRating;
import com.fastcampus.nextgraphql.service.DummyCourseService;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CourseRatingDataResolver {

    private final DummyCourseService courseService;

    public CourseRatingDataResolver(DummyCourseService courseService) {
        this.courseService = courseService;
    }

    @SchemaMapping(typeName = "CourseRating", field = "course")
    public Course getCourse(CourseRating courseRating) {
        return courseService.findCourseById(courseRating.getCourseId()).orElseThrow();
    }
}
