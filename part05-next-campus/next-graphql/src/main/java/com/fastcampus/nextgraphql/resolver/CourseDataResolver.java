package com.fastcampus.nextgraphql.resolver;

import com.fastcampus.nextgraphql.model.Course;
import com.fastcampus.nextgraphql.model.User;
import com.fastcampus.nextgraphql.service.DummyUserService;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CourseDataResolver {

    private final DummyUserService userService;

    public CourseDataResolver(DummyUserService userService) {
        this.userService = userService;
    }

    @SchemaMapping(typeName = "Course", field = "instructor")
    public User getInstructor(Course course) {
        return userService.findById(course.getInstructorId()).orElseThrow();
    }
}
