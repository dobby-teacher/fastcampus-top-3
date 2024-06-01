package com.fastcampus.nextgraphql.controller;

import com.fastcampus.nextgraphql.model.Enrollment;
import com.fastcampus.nextgraphql.model.Payment;
import com.fastcampus.nextgraphql.model.PlanSubscription;
import com.fastcampus.nextgraphql.service.EnrollmentService;
import com.fastcampus.nextgraphql.service.dummy.DummyEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @QueryMapping
    public List<Enrollment> getUserEnrollments(@Argument Long userId) {
        return enrollmentService.getEnrollmentsByUserId(userId);
    }

    @QueryMapping
    public List<PlanSubscription> getUserPlanSubscriptions(@Argument Long userId) {
        return enrollmentService.getSubscriptionsByUserId(userId);
    }

    @MutationMapping
    public Payment purchaseCourse(@Argument Long userId, @Argument Long courseId, @Argument Float amount, @Argument String paymentMethod) {
        return enrollmentService.purchaseCourse(userId, courseId, amount, paymentMethod);
    }

    @MutationMapping
    public Payment purchaseSubscription(@Argument Long userId, @Argument Float amount, @Argument String paymentMethod) {
        return enrollmentService.purchaseSubscription(userId, amount, paymentMethod);
    }

    @QueryMapping
    public boolean checkCourseAccess(@Argument Long userId, @Argument Long courseId) {
        // 구독 또는 개별 권한이 있는 경우 허용
        return enrollmentService.checkSubscriptionAccess(userId)
                || enrollmentService.checkCourseAccess(courseId, userId);
    }
}
