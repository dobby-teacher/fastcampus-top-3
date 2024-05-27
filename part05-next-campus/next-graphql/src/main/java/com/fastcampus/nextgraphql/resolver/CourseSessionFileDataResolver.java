package com.fastcampus.nextgraphql.resolver;

import com.fastcampus.nextgraphql.model.CourseSession;
import com.fastcampus.nextgraphql.model.CourseSessionFile;
import com.fastcampus.nextgraphql.service.DummyCourseSessionService;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CourseSessionFileDataResolver {

    private final DummyCourseSessionService courseSessionService;

    public CourseSessionFileDataResolver(DummyCourseSessionService courseSessionService) {
        this.courseSessionService = courseSessionService;
    }

    @SchemaMapping(typeName = "CourseSessionFile", field = "courseSession")
    public CourseSession getCourseSession(CourseSessionFile courseSessionFile) {
        return courseSessionService.findSessionById(courseSessionFile.getCourseSessionId()).orElseThrow();
    }
}
