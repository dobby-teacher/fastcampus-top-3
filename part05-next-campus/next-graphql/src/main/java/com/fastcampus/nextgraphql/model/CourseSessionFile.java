package com.fastcampus.nextgraphql.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseSessionFile implements Serializable {
    private Long fileId;
    private Long courseSessionId;
    private CourseSession courseSession;
    private String fileName;
    private String fileType;
    private String filePath;
}
