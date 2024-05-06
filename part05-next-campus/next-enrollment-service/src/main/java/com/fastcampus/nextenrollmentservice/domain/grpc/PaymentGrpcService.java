package com.fastcampus.nextenrollmentservice.domain.grpc;

import com.fastcampus.nextenrollmentservice.domain.entity.Payment;
import com.fastcampus.nextenrollmentservice.domain.entity.PaymentType;
import com.fastcampus.nextenrollmentservice.domain.service.EnrollmentServiceOuterClass;
import com.fastcampus.nextenrollmentservice.domain.service.FakePaymentServiceGrpc;
import com.fastcampus.nextenrollmentservice.domain.service.PaymentService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.math.BigDecimal;
import java.util.List;

@GrpcService
public class PaymentGrpcService extends FakePaymentServiceGrpc.FakePaymentServiceImplBase {

    private final PaymentService paymentService;

    public PaymentGrpcService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public void createPayment(EnrollmentServiceOuterClass.PaymentRequest request, StreamObserver<EnrollmentServiceOuterClass.PaymentResponse> responseObserver) {
        try {
            Payment payment = paymentService.createPayment(
                    request.getUserId(),
                    PaymentType.valueOf(request.getType()),
                    BigDecimal.valueOf(request.getAmount()),
                    request.getPaymentMethod()
            );

            responseObserver.onNext(payment.toProto());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void listUserPayments(EnrollmentServiceOuterClass.UserPaymentsRequest request, StreamObserver<EnrollmentServiceOuterClass.UserPaymentsResponse> responseObserver) {
        List<Payment> payments = paymentService.getUserPayments(request.getUserId());
        EnrollmentServiceOuterClass.UserPaymentsResponse response = EnrollmentServiceOuterClass.UserPaymentsResponse.newBuilder()
                .addAllPayments(payments.stream().map(Payment::toProto).toList())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
