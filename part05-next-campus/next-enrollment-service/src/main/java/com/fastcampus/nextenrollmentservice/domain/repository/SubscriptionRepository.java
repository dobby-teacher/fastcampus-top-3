package com.fastcampus.nextenrollmentservice.domain.repository;

import com.fastcampus.nextenrollmentservice.domain.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findTopByUserIdAndEndDateAfterOrderByEndDateDesc(Long userId, LocalDateTime endDate);
}
