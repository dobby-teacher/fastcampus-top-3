package com.fastcampus.nextcourseservice.domain.courses.service;

import com.fastcampus.nextcourseservice.domain.courses.entity.Course;
import com.fastcampus.nextcourseservice.domain.courses.entity.CourseSession;
import com.fastcampus.nextcourseservice.domain.courses.repository.CourseSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseSessionService {

    private final CourseSessionRepository sessionRepository;

    @Autowired
    public CourseSessionService(CourseSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public CourseSession addSessionToCourse(Long courseId, CourseSession session) {
        session.setCourse(new Course(courseId));
        return sessionRepository.save(session);
    }

    public CourseSession updateSession(Long sessionId, CourseSession sessionDetails) {
        CourseSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found with id " + sessionId));
        session.setTitle(sessionDetails.getTitle());
        return sessionRepository.save(session);
    }

    public Optional<CourseSession> getSessionById(Long sessionId) {
        return sessionRepository.findById(sessionId);
    }

    public List<CourseSession> getAllSessionsByCourseId(Long courseId) {
        return sessionRepository.findByCourseId(courseId);
    }
}
