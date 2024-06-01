package com.fastcampus.nextgraphql.service.dummy;

import com.fastcampus.nextgraphql.model.Enrollment;
import com.fastcampus.nextgraphql.model.Payment;
import com.fastcampus.nextgraphql.model.PlanSubscription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DummyEnrollmentService {

    private List<Enrollment> enrollments = new ArrayList<>();
    private List<PlanSubscription> subscriptions = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();
    private AtomicLong enrollmentIdGenerator = new AtomicLong(100);
    private AtomicLong subscriptionIdGenerator = new AtomicLong(100);
    private AtomicLong paymentIdGenerator = new AtomicLong(100);

    public DummyEnrollmentService() {
        initData();
    }

    private void initData() {
        // Initialize some dummy payments
        Long paymentId1 = paymentIdGenerator.incrementAndGet();
        Long paymentId2 = paymentIdGenerator.incrementAndGet();
        Long paymentId3 = paymentIdGenerator.incrementAndGet();
        Long paymentId4 = paymentIdGenerator.incrementAndGet();

        payments.add(new Payment(paymentIdGenerator.incrementAndGet(), 100L, null, "COURSE", 100.00f, "Credit Card", LocalDateTime.now().minusDays(10).toString()));
        payments.add(new Payment(paymentIdGenerator.incrementAndGet(), 100L, null, "SUBSCRIPTION", 120.00f, "PayPal", LocalDateTime.now().minusDays(15).toString()));

        // Initialize some dummy enrollments
        enrollments.add(new Enrollment(enrollmentIdGenerator.incrementAndGet(), 100L, null, 100L, null, paymentId1, null, LocalDateTime.now().minusDays(5).toString()));
        enrollments.add(new Enrollment(enrollmentIdGenerator.incrementAndGet(), 100L, null, 200L, null, paymentId2, null, LocalDateTime.now().minusDays(3).toString()));

        // Initialize some dummy subscriptions
        subscriptions.add(new PlanSubscription(subscriptionIdGenerator.incrementAndGet(), 101L, null, paymentId3, null, LocalDateTime.now().minusDays(300).toString(), LocalDateTime.now().plusDays(65).toString(), "Expired"));
        subscriptions.add(new PlanSubscription(subscriptionIdGenerator.incrementAndGet(), 102L, null, paymentId4, null, LocalDateTime.now().minusDays(200).toString(), LocalDateTime.now().plusDays(160).toString(), "Expired"));
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

    public Payment purchaseCourse(long userId, long courseId, double amount, String paymentMethod) {
        Payment newPayment = new Payment(paymentIdGenerator.incrementAndGet(), userId, null, "COURSE", (float) amount, paymentMethod, LocalDateTime.now().toString());
        payments.add(newPayment);
        return newPayment;
    }

    public Payment purchaseSubscription(long userId, double amount, String paymentMethod) {
        Payment newPayment = new Payment(paymentIdGenerator.incrementAndGet(), userId, null, "SUBSCRIPTION", (float) amount, paymentMethod, LocalDateTime.now().toString());
        payments.add(newPayment);
        return newPayment;
    }

    public boolean checkCourseAccess(long courseId, long userId) {
        return enrollments.stream().anyMatch(e -> e.getUserId() == userId && e.getCourseId() == courseId);
    }

    public boolean checkSubscriptionAccess(long userId) {
        LocalDateTime now = LocalDateTime.now();
        return subscriptions.stream().anyMatch(s -> s.getUserId() == userId && (now.isAfter(LocalDateTime.parse(s.getStartDate())) && now.isBefore(LocalDateTime.parse(s.getEndDate()))));
    }

    public Payment findPaymentById(Long paymentId) {
        return payments.stream().filter(payment -> payment.getId().equals(paymentId)).findFirst().orElseThrow();
    }
}
