package com.fastcampus.nextgraphql.service;

import com.fastcampus.nextgraphql.model.Course;
import com.fastcampus.nextgraphql.model.CourseRating;
import com.fastcampus.nextgraphql.model.CourseSession;
import com.fastcampus.nextgraphql.model.CourseSessionFile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class DummyCourseService {
    private final List<Course> courses = new ArrayList<>();
    private final List<CourseSession> sessions = new ArrayList<>();
    private final List<CourseRating> ratings = new ArrayList<>();
    private final AtomicLong courseCounter = new AtomicLong();
    private final AtomicLong sessionCounter = new AtomicLong();
    private final AtomicLong ratingCounter = new AtomicLong();

    private final DummyFileService fileService; // Injecting the file service

    public DummyCourseService(DummyFileService fileService) {
        this.fileService = fileService;
    }

    public List<Course> findAllCourses() {
        return new ArrayList<>(courses);
    }

    public Optional<Course> findCourseById(Long courseId) {
        return courses.stream()
                .filter(course -> course.getId().equals(courseId))
                .findFirst();
    }

    public Course createCourse(String title, String description, Long instructorId) {
        Course newCourse = new Course(courseCounter.incrementAndGet(), title, description, instructorId, new ArrayList<>(), new ArrayList<>());
        courses.add(newCourse);
        return newCourse;
    }

    public Course updateCourse(Long id, String title, String description) {
        Course course = findCourseById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        course.setTitle(title);
        course.setDescription(description);
        return course;
    }

    public List<CourseSession> findAllSessionsByCourseId(Long courseId) {
        return sessions.stream()
                .filter(session -> session.getCourseId().equals(courseId))
                .collect(Collectors.toList());
    }

    public CourseSession addSessionToCourse(Long courseId, String title) {
        CourseSession newSession = new CourseSession(sessionCounter.incrementAndGet(), courseId, title, new ArrayList<>());
        sessions.add(newSession);
        findCourseById(courseId).ifPresent(course -> course.getCourseSessions().add(newSession));
        return newSession;
    }

    public List<CourseSessionFile> findAllSessionFilesBySessionId(Long sessionId) {
        return fileService.findFilesBySessionId(sessionId);
    }

    public CourseRating addRatingToCourse(Long userId, Long courseId, Integer rating, String comment) {
        CourseRating newRating = new CourseRating(ratingCounter.incrementAndGet(), courseId, userId, rating, comment);
        ratings.add(newRating);
        findCourseById(courseId).ifPresent(course -> course.getRatings().add(newRating));
        return newRating;
    }
}
