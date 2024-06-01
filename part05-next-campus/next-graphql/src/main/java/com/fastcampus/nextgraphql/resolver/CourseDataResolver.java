package com.fastcampus.nextgraphql.resolver;

import com.fastcampus.nextgraphql.model.Course;
import com.fastcampus.nextgraphql.model.CourseSession;
import com.fastcampus.nextgraphql.model.User;
import com.fastcampus.nextgraphql.service.CourseService;
import com.fastcampus.nextgraphql.service.UserService;
import com.fastcampus.nextgraphql.service.dummy.DummyCourseService;
import com.fastcampus.nextgraphql.service.dummy.DummyUserService;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CourseDataResolver {
    private final CourseService courseService;
    private final UserService userService;

    public CourseDataResolver(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @SchemaMapping(typeName = "Course", field = "courseSessions")
    public List<CourseSession> getSessions(Course course) {
        return courseService.findAllSessionsByCourseId(course.getId());
    }

    @SchemaMapping(typeName = "Course", field = "instructor")
    public User getInstructor(Course course) {
        return userService.findById(course.getInstructorId()).orElseThrow();
    }
}
