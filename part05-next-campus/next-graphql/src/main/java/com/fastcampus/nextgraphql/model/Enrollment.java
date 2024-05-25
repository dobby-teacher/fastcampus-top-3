package com.fastcampus.nextgraphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    private Long id;
    private User user;
    private Course course;
    private Payment payment;
    private String registrationDate;
}
