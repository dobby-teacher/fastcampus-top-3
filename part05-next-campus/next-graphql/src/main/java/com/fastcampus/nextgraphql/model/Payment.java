package com.fastcampus.nextgraphql.model;

import com.fastcampus.nextenrollmentservice.domain.service.EnrollmentServiceOuterClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment implements Serializable {
    private Long id;
    private Long userId;
    private User user;
    private String paymentType;
    private Float amount;
    private String paymentMethod;
    private String paymentDate;

    public static Payment fromProto(EnrollmentServiceOuterClass.PaymentResponse proto) {
        Payment payment = new Payment();
        payment.setId(proto.getPaymentId());
        payment.setUserId(proto.getUserId());
        payment.setPaymentType(proto.getType());
        payment.setAmount((float) proto.getAmount());
        payment.setPaymentMethod(proto.getPaymentMethod());
        payment.setPaymentDate(Instant.ofEpochMilli(proto.getPaymentDate()).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return payment;
    }
}
