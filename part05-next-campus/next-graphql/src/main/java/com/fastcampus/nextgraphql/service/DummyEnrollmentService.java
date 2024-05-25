package com.fastcampus.nextgraphql.service;

import com.fastcampus.nextgraphql.model.Enrollment;
import com.fastcampus.nextgraphql.model.Payment;
import com.fastcampus.nextgraphql.model.PlanSubscription;
import com.fastcampus.nextgraphql.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DummyEnrollmentService {
    private List<Enrollment> enrollments = new ArrayList<>();  // This is a simple in-memory list for demo purposes

    // In EnrollmentService.java
    public List<Enrollment> getEnrollmentsByUserId(Long userId) {
        // Implement database query to fetch enrollments by user ID
        return new ArrayList<>();
    }

    // In SubscriptionService.java
    public List<PlanSubscription> getSubscriptionsByUserId(Long userId) {
        // Implement database query to fetch subscriptions by user ID
        return new ArrayList<>();
    }

    // In PaymentService.java
    public Payment createPayment(Long userId, Float amount, String paymentType, String paymentMethod) {
        // Create a new Payment object and save it to the database
        return new Payment(1L, new User(userId, "Example User", "user@example.com"), paymentType, amount, paymentMethod, "2024-01-01");
    }
}
