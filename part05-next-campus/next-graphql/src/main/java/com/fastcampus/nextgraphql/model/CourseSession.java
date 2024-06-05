package com.fastcampus.nextgraphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseSession implements Serializable {
    private Long id;
    private Long courseId;
    private String title;
    private List<CourseSessionFile> files;
}
