package com.fastcampus.nextgraphql.resolver;

import com.fastcampus.nextgraphql.model.CourseSession;
import com.fastcampus.nextgraphql.model.CourseSessionFile;
import com.fastcampus.nextgraphql.service.DummyFileService;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class CourseSessionDataResolver {

    private final DummyFileService fileService;

    public CourseSessionDataResolver(DummyFileService fileService) {
        this.fileService = fileService;
    }

    @SchemaMapping(typeName = "CourseSession", field = "files")
    public List<CourseSessionFile> getFiles(CourseSession courseSession) {
        return fileService.findFilesBySessionId(courseSession.getId());
    }
}
