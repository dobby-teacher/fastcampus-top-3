package com.fastcampus.nextenrollmentservice.domain.service;

import com.fastcampus.nextenrollmentservice.domain.entity.Enrollment;
import com.fastcampus.nextenrollmentservice.domain.entity.Subscription;
import com.fastcampus.nextenrollmentservice.domain.repository.EnrollmentRepository;
import com.fastcampus.nextenrollmentservice.domain.repository.SubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    @Test
    void testRegisterCourse() {
        long userId = 1L;
        long courseId = 100L;
        long paymentId = 200L;

        Enrollment mockEnrollment = new Enrollment();
        mockEnrollment.setUserId(userId);
        mockEnrollment.setCourseId(courseId);
        mockEnrollment.setPaymentId(paymentId);
        mockEnrollment.setRegistrationDate(LocalDateTime.now());

        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(mockEnrollment);

        Enrollment result = enrollmentService.registerCourse(userId, courseId, paymentId);

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(courseId, result.getCourseId());
        assertEquals(paymentId, result.getPaymentId());
        verify(enrollmentRepository).save(any(Enrollment.class));
    }

    @Test
    void testManageSubscription() {
        long userId = 1L;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(30);
        long paymentId = 1L;

        Subscription mockSubscription = new Subscription();
        mockSubscription.setUserId(userId);
        mockSubscription.setStartDate(startDate);
        mockSubscription.setEndDate(endDate);
        mockSubscription.setPaymentId(paymentId);

        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(mockSubscription);

        Subscription result = enrollmentService.manageSubscription(userId, startDate, endDate, paymentId);

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(startDate, result.getStartDate());
        assertEquals(endDate, result.getEndDate());
        verify(subscriptionRepository).save(any(Subscription.class));
    }

    @Test
    void testRenewSubscription() {
        long subscriptionId = 300L;
        LocalDateTime newStartDate = LocalDateTime.now();
        LocalDateTime newEndDate = LocalDateTime.now().plusDays(30);

        Subscription foundSubscription = new Subscription();
        foundSubscription.setStartDate(LocalDateTime.now().minusDays(15));
        foundSubscription.setEndDate(LocalDateTime.now().minusDays(1));

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(foundSubscription));
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(foundSubscription);

        Subscription result = enrollmentService.renewSubscription(subscriptionId, newStartDate, newEndDate);

        assertNotNull(result);
        assertEquals(newStartDate, result.getStartDate());
        assertEquals(newEndDate, result.getEndDate());
        verify(subscriptionRepository).save(any(Subscription.class));
    }

    @Test
    void testRenewSubscription_NotFound() {
        long subscriptionId = 300L;
        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> {
            enrollmentService.renewSubscription(subscriptionId, LocalDateTime.now(), LocalDateTime.now().plusDays(30));
        });
    }

    @Test
    void testCheckCourseAccess() {
        long userId = 1L;
        long courseId = 100L;
        when(enrollmentRepository.findByUserIdAndCourseId(userId, courseId)).thenReturn(Optional.of(new Enrollment()));

        assertTrue(enrollmentService.checkCourseAccess(userId, courseId));
        verify(enrollmentRepository).findByUserIdAndCourseId(userId, courseId);
    }

    @Test
    void testCheckSubscriptionAccess() {
        long userId = 1L;
        LocalDateTime now = LocalDateTime.now();
        Subscription subscription = new Subscription();
        subscription.setEndDate(now.plusDays(5));

        when(subscriptionRepository.findTopByUserIdAndEndDateAfterOrderByEndDateDesc(eq(userId), any())).thenReturn(Optional.of(subscription));

        assertTrue(enrollmentService.checkSubscriptionAccess(userId, now));
        verify(subscriptionRepository).findTopByUserIdAndEndDateAfterOrderByEndDateDesc(userId, now);
    }
}
