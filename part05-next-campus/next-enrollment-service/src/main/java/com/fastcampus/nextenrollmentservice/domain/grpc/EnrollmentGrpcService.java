package com.fastcampus.nextenrollmentservice.domain.grpc;

import com.fastcampus.nextenrollmentservice.domain.entity.Enrollment;
import com.fastcampus.nextenrollmentservice.domain.entity.Subscription;
import com.fastcampus.nextenrollmentservice.domain.service.EnrollmentService;
import com.fastcampus.nextenrollmentservice.domain.service.EnrollmentServiceGrpc;
import com.fastcampus.nextenrollmentservice.domain.service.EnrollmentServiceOuterClass;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@GrpcService
@Slf4j
public class EnrollmentGrpcService extends EnrollmentServiceGrpc.EnrollmentServiceImplBase {
    private final EnrollmentService enrollmentService;

    public EnrollmentGrpcService(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @Override
    public void registerCourse(EnrollmentServiceOuterClass.CourseRegistrationRequest request, StreamObserver<EnrollmentServiceOuterClass.CourseRegistrationResponse> responseObserver) {
        try {
            Enrollment enrollment = enrollmentService.registerCourse(request.getUserId(), request.getCourseId(), request.getPaymentId());
            EnrollmentServiceOuterClass.CourseRegistrationResponse response = EnrollmentServiceOuterClass.CourseRegistrationResponse.newBuilder()
                    .setCourseId(enrollment.getCourseId())
                    .setUserId(enrollment.getUserId())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("registerCourse error : ", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void manageSubscription(EnrollmentServiceOuterClass.SubscriptionRequest request, StreamObserver<EnrollmentServiceOuterClass.SubscriptionResponse> responseObserver) {
        try {
            LocalDateTime startDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(request.getStartDate()), ZoneId.systemDefault());
            LocalDateTime endDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(request.getEndDate()), ZoneId.systemDefault());

            Subscription subscription = enrollmentService.manageSubscription(
                    request.getUserId(),
                    startDate,
                    endDate);
            EnrollmentServiceOuterClass.SubscriptionResponse response = EnrollmentServiceOuterClass.SubscriptionResponse.newBuilder()
                    .setSubscriptionId(subscription.getSubscriptionId())
                    .setUserId(subscription.getUserId())
                    .setStartDate(subscription.getStartDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .setEndDate(subscription.getEndDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("manageSubscription error : ", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void renewSubscription(EnrollmentServiceOuterClass.RenewSubscriptionRequest request, StreamObserver<EnrollmentServiceOuterClass.SubscriptionResponse> responseObserver) {
        try {
            LocalDateTime startDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(request.getStartDate()), ZoneId.systemDefault());
            LocalDateTime endDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(request.getEndDate()), ZoneId.systemDefault());

            Subscription subscription = enrollmentService.renewSubscription(
                    request.getSubscriptionId(),
                    startDate,
                    endDate);
            EnrollmentServiceOuterClass.SubscriptionResponse response = EnrollmentServiceOuterClass.SubscriptionResponse.newBuilder()
                    .setSubscriptionId(subscription.getSubscriptionId())
                    .setUserId(subscription.getUserId())
                    .setStartDate(subscription.getStartDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .setEndDate(subscription.getEndDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("renewSubscription error : ", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void checkCourseAccess(EnrollmentServiceOuterClass.CourseAccessRequest request, StreamObserver<EnrollmentServiceOuterClass.CourseAccessResponse> responseObserver) {
        boolean hasAccess = enrollmentService.checkCourseAccess(request.getUserId(), request.getCourseId());
        EnrollmentServiceOuterClass.CourseAccessResponse response = EnrollmentServiceOuterClass.CourseAccessResponse.newBuilder()
                .setHasAccess(hasAccess)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void checkSubscriptionAccess(EnrollmentServiceOuterClass.SubscriptionAccessRequest request, StreamObserver<EnrollmentServiceOuterClass.SubscriptionAccessResponse> responseObserver) {
        boolean hasAccess = enrollmentService.checkSubscriptionAccess(request.getUserId(), LocalDateTime.now());
        EnrollmentServiceOuterClass.SubscriptionAccessResponse response = EnrollmentServiceOuterClass.SubscriptionAccessResponse.newBuilder()
                .setHasAccess(hasAccess)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
