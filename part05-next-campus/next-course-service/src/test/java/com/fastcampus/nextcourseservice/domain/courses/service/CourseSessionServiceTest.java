package com.fastcampus.nextcourseservice.domain.courses.service;

import com.fastcampus.nextcourseservice.domain.courses.entity.CourseSession;
import com.fastcampus.nextcourseservice.domain.courses.repository.CourseSessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CourseSessionServiceTest {

    @Mock
    private CourseSessionRepository sessionRepository;

    @InjectMocks
    private CourseSessionService courseSessionService;

    @Test
    public void testAddSessionToCourse() {
        Long courseId = 1L;
        CourseSession session = new CourseSession();
        when(sessionRepository.save(any(CourseSession.class))).thenReturn(session);

        CourseSession result = courseSessionService.addSessionToCourse(courseId, session);

        assertNotNull(result);
        verify(sessionRepository).save(session);
        assertEquals(courseId, session.getCourse().getId());
    }

    @Test
    public void testUpdateSession() {
        Long sessionId = 1L;
        CourseSession existingSession = new CourseSession();
        existingSession.setId(sessionId);
        existingSession.setTitle("Original Title");

        CourseSession updatedDetails = new CourseSession();
        updatedDetails.setTitle("Updated Title");

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(existingSession));
        when(sessionRepository.save(any(CourseSession.class))).thenReturn(updatedDetails);

        CourseSession result = courseSessionService.updateSession(sessionId, updatedDetails);

        assertNotNull(result);
        verify(sessionRepository).findById(sessionId);
        verify(sessionRepository).save(existingSession);
        assertEquals("Updated Title", result.getTitle());
    }

    @Test
    public void testGetSessionById() {
        Long sessionId = 1L;
        CourseSession session = new CourseSession();
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        Optional<CourseSession> result = courseSessionService.getSessionById(sessionId);

        assertTrue(result.isPresent());
        assertSame(session, result.get());
        verify(sessionRepository).findById(sessionId);
    }

    @Test
    public void testGetAllSessionsByCourseId() {
        Long courseId = 1L;
        List<CourseSession> sessions = Arrays.asList(new CourseSession(), new CourseSession());
        when(sessionRepository.findByCourseId(courseId)).thenReturn(sessions);

        List<CourseSession> result = courseSessionService.getAllSessionsByCourseId(courseId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(sessionRepository).findByCourseId(courseId);
    }
}
