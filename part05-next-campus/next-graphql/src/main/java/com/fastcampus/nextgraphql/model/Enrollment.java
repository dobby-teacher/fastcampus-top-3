package com.fastcampus.nextgraphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    private Long id;
    private Long userId;
    private User user;
    private Long courseId;
    private Course course;
    private Long paymentId;
    private Payment payment;
    private String registrationDate;
}
