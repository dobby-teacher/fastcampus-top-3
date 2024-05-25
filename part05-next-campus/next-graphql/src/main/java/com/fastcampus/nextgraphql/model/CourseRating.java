package com.fastcampus.nextgraphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRating {
    private Long id;
    private Long courseId;
    private Long userId;
    private Integer rating;
    private String comment;
}
