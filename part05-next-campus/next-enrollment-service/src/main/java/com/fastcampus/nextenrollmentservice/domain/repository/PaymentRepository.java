package com.fastcampus.nextenrollmentservice.domain.repository;

import com.fastcampus.nextenrollmentservice.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(long userId);
}
