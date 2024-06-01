package com.fastcampus.nextgraphql.resolver;

import com.fastcampus.nextgraphql.model.CourseSession;
import com.fastcampus.nextgraphql.model.CourseSessionFile;
import com.fastcampus.nextgraphql.service.CourseService;
import com.fastcampus.nextgraphql.service.dummy.DummyCourseService;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CourseSessionFileDataResolver {

    private final CourseService courseService;

    public CourseSessionFileDataResolver(CourseService courseService) {
        this.courseService = courseService;
    }

    @SchemaMapping(typeName = "CourseSessionFile", field = "courseSession")
    public CourseSession getCourseSession(CourseSessionFile courseSessionFile) {
        return courseService.findSessionById(null, courseSessionFile.getCourseSessionId()).orElseThrow();
    }
}
