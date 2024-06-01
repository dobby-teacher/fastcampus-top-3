package com.fastcampus.nextenrollmentservice.domain.entity;

import com.fastcampus.nextenrollmentservice.domain.service.EnrollmentServiceOuterClass;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@Entity
@Table(name = "enrollments")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long courseId;

    @Column(name = "payment_id",nullable = false)
    private Long paymentId;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", insertable = false, updatable = false)
    private Payment payment;

    public EnrollmentServiceOuterClass.Enrollment toProto() {
        return EnrollmentServiceOuterClass.Enrollment.newBuilder()
                .setEnrollmentId(this.enrollmentId)
                .setUserId(this.userId)
                .setCourseId(this.courseId)
                .setPaymentId(this.paymentId)
                .setRegistrationDate(this.registrationDate.atZone(ZoneId.systemDefault()).toEpochSecond())
                .build();
    }
}
