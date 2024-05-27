package com.fastcampus.nextgraphql.controller;

import com.fastcampus.nextgraphql.model.Enrollment;
import com.fastcampus.nextgraphql.model.Payment;
import com.fastcampus.nextgraphql.model.PlanSubscription;
import com.fastcampus.nextgraphql.service.DummyEnrollmentService;
import com.fastcampus.nextgraphql.service.DummyPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class EnrollmentController {
    private final DummyEnrollmentService enrollmentService;
    private final DummyPaymentService paymentService;

    @Autowired
    public EnrollmentController(DummyEnrollmentService enrollmentService, DummyPaymentService paymentService) {
        this.enrollmentService = enrollmentService;
        this.paymentService = paymentService;
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
    public Payment createPayment(@Argument Long userId, @Argument Float amount, @Argument String paymentType, @Argument String paymentMethod) {
        return paymentService.createPayment(userId, amount, paymentType, paymentMethod);
    }
}
