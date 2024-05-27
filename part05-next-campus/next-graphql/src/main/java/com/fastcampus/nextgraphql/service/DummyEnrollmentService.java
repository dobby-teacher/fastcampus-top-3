package com.fastcampus.nextgraphql.service;

import com.fastcampus.nextgraphql.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DummyEnrollmentService {
    private List<Enrollment> enrollments = new ArrayList<>();
    private List<PlanSubscription> subscriptions = new ArrayList<>();

    public DummyEnrollmentService() {
        // Initialize with dummy data
        initData();
    }

    private void initData() {
        // Dummy enrollments
        enrollments.add(new Enrollment(1L, 101L, null, 1L, null, 1L, null, "2024-01-12"));

        // Dummy subscriptions
        subscriptions.add(new PlanSubscription(2L, 102L, null, 2L, null, "2024-01-01", "2025-01-01"));
    }

    public List<Enrollment> getEnrollmentsByUserId(Long userId) {
        return enrollments.stream()
                .filter(enrollment -> enrollment.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<PlanSubscription> getSubscriptionsByUserId(Long userId) {
        return subscriptions.stream()
                .filter(subscription -> subscription.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
