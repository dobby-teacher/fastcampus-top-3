package com.fastcampus.nextenrollmentservice.domain.entity;

import com.fastcampus.nextenrollmentservice.domain.service.EnrollmentServiceOuterClass;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.ZoneId;

@Data
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String paymentMethod;

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    public EnrollmentServiceOuterClass.PaymentResponse toProto() {
        EnrollmentServiceOuterClass.PaymentResponse.Builder builder = EnrollmentServiceOuterClass.PaymentResponse.newBuilder();

        if (this.paymentId != null) {
            builder.setPaymentId(this.paymentId);
            builder.setPaymentSuccessful(this.paymentId != null);
        }
        if (this.userId != null) {
            builder.setUserId(this.userId);
        }
        if (this.paymentType != null) {
            builder.setType(this.paymentType.name());
        }
        if (this.amount != null) {
            builder.setAmount(this.amount.doubleValue());
        }
        if (this.paymentMethod != null) {
            builder.setPaymentMethod(this.paymentMethod);
        }
        if (this.paymentDate != null) {
            builder.setPaymentDate(this.paymentDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        }

        return builder.build();
    }
}
