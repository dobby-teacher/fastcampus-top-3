package com.fastcampus.nextgraphql.resolver;

import com.fastcampus.nextgraphql.model.Course;
import com.fastcampus.nextgraphql.model.CourseRating;
import com.fastcampus.nextgraphql.model.User;
import com.fastcampus.nextgraphql.service.CourseService;
import com.fastcampus.nextgraphql.service.UserService;
import com.fastcampus.nextgraphql.service.dummy.DummyCourseService;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CourseRatingDataResolver {

    private final CourseService courseService;
    private final UserService userService;

    public CourseRatingDataResolver(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @SchemaMapping(typeName = "CourseRating", field = "course")
    public Course getCourse(CourseRating courseRating) {
        return courseService.findCourseById(courseRating.getCourseId()).orElseThrow();
    }

    @SchemaMapping(typeName = "CourseRating", field = "user")
    public User getUser(CourseRating courseRating) {
        return userService.findById(courseRating.getUserId()).orElseThrow();
    }
}
