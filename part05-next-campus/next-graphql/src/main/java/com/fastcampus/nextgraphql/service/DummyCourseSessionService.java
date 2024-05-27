package com.fastcampus.nextgraphql.service;

import com.fastcampus.nextgraphql.model.CourseSession;
import com.fastcampus.nextgraphql.model.CourseSessionFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
@Service
    public class DummyCourseSessionService {
    private final List<CourseSession> sessions = new ArrayList<>();
    private final AtomicLong sessionCounter = new AtomicLong();

    private final DummyFileService fileService; // Manage files associated with sessions

    public DummyCourseSessionService(DummyFileService fileService) {
        this.fileService = fileService;

        initData();
    }

    private void initData() {
        // Adding dummy course sessions
        sessions.add(new CourseSession(sessionCounter.incrementAndGet(), 1L, "GraphQL Basics", new ArrayList<>()));
        sessions.add(new CourseSession(sessionCounter.incrementAndGet(), 1L, "Advanced GraphQL Queries", new ArrayList<>()));
        sessions.add(new CourseSession(sessionCounter.incrementAndGet(), 2L, "Optimizing GraphQL Performance", new ArrayList<>()));
        sessions.add(new CourseSession(sessionCounter.incrementAndGet(), 3L, "Introduction to GraphQL Security", new ArrayList<>()));
    }

    public List<CourseSession> findAllSessions() {
        return new ArrayList<>(sessions);
    }

    public Optional<CourseSession> findSessionById(Long sessionId) {
        return sessions.stream()
                       .filter(session -> session.getId().equals(sessionId))
                       .findFirst();
    }

    public CourseSession addSession(Long courseId, String title) {
        CourseSession newSession = new CourseSession(sessionCounter.incrementAndGet(), courseId, title, new ArrayList<>());
        sessions.add(newSession);
        return newSession;
    }

    public List<CourseSessionFile> findFilesBySessionId(Long sessionId) {
        return fileService.findFilesBySessionId(sessionId);
    }

    public CourseSession updateSession(Long sessionId, String title) {
        CourseSession session = findSessionById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        session.setTitle(title);
        return session;
    }
}
