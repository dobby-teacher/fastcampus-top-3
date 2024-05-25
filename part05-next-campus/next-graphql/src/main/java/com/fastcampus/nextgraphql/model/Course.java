package com.fastcampus.nextgraphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private Long id;
    private String title;
    private String description;
    private Long instructorId;
    private List<CourseSession> courseSessions;  // Connections to course sessions
    private List<CourseRating> ratings;          // Connections to course ratings
}
