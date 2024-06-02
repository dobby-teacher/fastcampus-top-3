package com.fastcampus.nextgraphql.service.dummy;

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
    private final AtomicLong courseCounter = new AtomicLong(100);
    private final AtomicLong sessionCounter = new AtomicLong(100);
    private final AtomicLong ratingCounter = new AtomicLong(100);

    public DummyCourseService() {
        initData();
    }

    private void initData() {
        // Adding dummy courses
        courses.add(new Course(courseCounter.incrementAndGet(), "Introduction to GraphQL", "Learn the basics of GraphQL", 101L, new ArrayList<>(), new ArrayList<>()));
        courses.add(new Course(courseCounter.incrementAndGet(), "Advanced GraphQL", "Deep dive into GraphQL", 102L, new ArrayList<>(), new ArrayList<>()));

        // Adding dummy sessions
        sessions.add(new CourseSession(sessionCounter.incrementAndGet(), 100L, "Session 1: Basics", new ArrayList<>()));
        sessions.add(new CourseSession(sessionCounter.incrementAndGet(), 100L, "Session 2: Queries", new ArrayList<>()));
        sessions.add(new CourseSession(sessionCounter.incrementAndGet(), 100L, "Session 1: Performance", new ArrayList<>()));

        // Linking sessions to courses
        courses.get(0).getCourseSessions().add(sessions.get(0));
        courses.get(0).getCourseSessions().add(sessions.get(1));
        courses.get(1).getCourseSessions().add(sessions.get(2));

        // Adding dummy ratings
        ratings.add(new CourseRating(ratingCounter.incrementAndGet(), 1L, 201L, 5, "Excellent course!"));
        ratings.add(new CourseRating(ratingCounter.incrementAndGet(), 1L, 202L, 4, "Very informative."));

        // Linking ratings to courses
        courses.get(0).getRatings().add(ratings.get(0));
        courses.get(0).getRatings().add(ratings.get(1));
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
        return courses.stream()
                .filter(courses -> courses.getId().equals(courseId))
                .toList()
                .get(0)
                .getCourseSessions();
    }

    public CourseSession addSessionToCourse(Long courseId, String title) {
        CourseSession newSession = new CourseSession(sessionCounter.incrementAndGet(), courseId, title, new ArrayList<>());
        sessions.add(newSession);
        findCourseById(courseId).ifPresent(course -> course.getCourseSessions().add(newSession));
        return newSession;
    }

    public CourseRating addRatingToCourse(Long userId, Long courseId, Integer rating, String comment) {
        CourseRating newRating = new CourseRating(ratingCounter.incrementAndGet(), courseId, userId, rating, comment);
        ratings.add(newRating);
        findCourseById(courseId).ifPresent(course -> course.getRatings().add(newRating));
        return newRating;
    }

    public Optional<CourseSession> findSessionById(Long sessionId) {
        return sessions.stream()
                .filter(session -> session.getId().equals(sessionId))
                .findFirst();
    }
}
