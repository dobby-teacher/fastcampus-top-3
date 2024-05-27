package com.fastcampus.nextgraphql.service;

import com.fastcampus.nextgraphql.model.Payment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class DummyPaymentService {
    private List<Payment> payments = new ArrayList<>();
    private AtomicLong paymentCounter = new AtomicLong();

    public DummyPaymentService() {
        initData();
    }

    private void initData() {
        Payment payment1 = new Payment(1L, 101L, null, "COURSE", 200.00f, "Credit Card", "2024-01-10");
        Payment payment2 = new Payment(2L, 102L, null, "SUBSCRIPTION", 150.00f, "PayPal", "2024-01-15");

        payments.add(payment1);
        payments.add(payment2);
    }

    public List<Payment> findAllPayments() {
        return new ArrayList<>(payments);
    }

    public Optional<Payment> findPaymentById(Long paymentId) {
        return payments.stream()
                       .filter(payment -> payment.getId().equals(paymentId))
                       .findFirst();
    }

    public Payment createPayment(Long userId, Float amount, String paymentType, String paymentMethod) {
        Payment newPayment = new Payment(paymentCounter.incrementAndGet(), 1L, null, paymentType, amount, paymentMethod, new Date().toString());
        payments.add(newPayment);
        return newPayment;
    }
}
