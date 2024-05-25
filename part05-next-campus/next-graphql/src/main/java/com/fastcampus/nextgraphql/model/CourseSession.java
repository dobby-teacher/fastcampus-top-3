package com.fastcampus.nextgraphql.model;

import com.fastcampus.nextgraphql.model.CourseSessionFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseSession {
    private Long id;
    private Long courseId;
    private String title;
    private List<CourseSessionFile> files;
}
