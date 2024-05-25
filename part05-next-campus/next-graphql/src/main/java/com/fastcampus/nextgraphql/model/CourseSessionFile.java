package com.fastcampus.nextgraphql.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseSessionFile {
    private Long fileId;
    private Long sessionId;
    private String fileName;
    private String fileType;
    private String filePath;  // This would typically represent a location on disk or a URL
}
