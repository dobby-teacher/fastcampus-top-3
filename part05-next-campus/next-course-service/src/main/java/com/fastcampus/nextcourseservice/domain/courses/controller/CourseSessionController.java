package com.fastcampus.nextcourseservice.domain.courses.controller;

import com.fastcampus.nextcourseservice.domain.courses.entity.CourseSession;
import com.fastcampus.nextcourseservice.domain.courses.service.CourseSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses/{courseId}/sessions")
public class CourseSessionController {

    private final CourseSessionService sessionService;

    @Autowired
    public CourseSessionController(CourseSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<CourseSession> addSessionToCourse(@PathVariable Long courseId, @RequestBody CourseSession session) {
        CourseSession newSession = sessionService.addSessionToCourse(courseId, session);
        return ResponseEntity.ok(newSession);
    }

    @PutMapping("/{sessionId}")
    public ResponseEntity<CourseSession> updateSession(@PathVariable Long sessionId, @RequestBody CourseSession session) {
        CourseSession updatedSession = sessionService.updateSession(sessionId, session);
        return ResponseEntity.ok(updatedSession);
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<CourseSession> getSessionById(@PathVariable Long sessionId) {
        CourseSession session = sessionService.getSessionById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found with id " + sessionId));
        return ResponseEntity.ok(session);
    }

    @GetMapping
    public ResponseEntity<List<CourseSession>> getAllSessionsByCourseId(@PathVariable Long courseId) {
        List<CourseSession> sessions = sessionService.getAllSessionsByCourseId(courseId);
        return ResponseEntity.ok(sessions);
    }
}
