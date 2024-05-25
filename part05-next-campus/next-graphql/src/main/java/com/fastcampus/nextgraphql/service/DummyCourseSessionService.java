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

    @Autowired
    private DummyFileService fileService; // Manage files associated with sessions

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
