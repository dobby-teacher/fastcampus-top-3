package com.fastcampus.nextgraphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRating implements Serializable {
    private Long id;
    private Long courseId;
    private Long userId;
    private Integer rating;
    private String comment;
}
