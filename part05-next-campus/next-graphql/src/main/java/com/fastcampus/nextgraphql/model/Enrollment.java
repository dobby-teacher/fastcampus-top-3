package com.fastcampus.nextgraphql.model;

import com.fastcampus.nextenrollmentservice.domain.service.EnrollmentServiceOuterClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

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

    public static Enrollment fromProto(EnrollmentServiceOuterClass.Enrollment proto) {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(proto.getEnrollmentId());
        enrollment.setUserId(proto.getUserId());
        enrollment.setCourseId(proto.getCourseId());
        enrollment.setPaymentId(proto.getPaymentId());
        enrollment.setRegistrationDate(LocalDateTime.ofInstant(Instant.ofEpochSecond(proto.getRegistrationDate()), ZoneId.systemDefault()).toString());
        return enrollment;
    }

}
