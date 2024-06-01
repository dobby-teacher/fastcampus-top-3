package com.fastcampus.nextgraphql.service;

import com.fastcampus.nextgraphql.model.Course;
import com.fastcampus.nextgraphql.model.CourseRating;
import com.fastcampus.nextgraphql.model.CourseSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CourseService {

    private final RestTemplate restTemplate;

    @Autowired
    public CourseService(@LoadBalanced RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Course createCourse(String title, String description, Long instructorId) {
        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);
        course.setInstructorId(instructorId);

        return restTemplate.postForObject("http://next-course-service/courses", course, Course.class);
    }

    public Course updateCourse(Long id, String title, String description) {
        Course course = new Course();
        course.setId(id);
        course.setTitle(title);
        course.setDescription(description);

        restTemplate.put("http://next-course-service/courses/" + id, course);
        return course;
    }

    public Optional<Course> findCourseById(Long courseId) {
        Course course = null;
        try {
            course = restTemplate.getForObject("http://next-course-service/courses/" + courseId, Course.class);
        } catch (Exception e) {
            log.error("An error occurred while fetching the course: " + e.getMessage(), e);
        }

        return Optional.ofNullable(course);
    }

    public List<Course> findAllCourses() {
        Course[] courses = restTemplate.getForObject("http://next-course-service/courses", Course[].class);
        if (courses == null) {
            return Collections.emptyList();
        }

        return Arrays.asList(courses);
    }

    public CourseSession addSessionToCourse(Long courseId, String title) {
        CourseSession courseSession = new CourseSession();
        courseSession.setTitle(title);

        return restTemplate.postForObject(
                UriComponentsBuilder.fromUriString("http://next-course-service/courses/{courseId}/sessions")
                        .buildAndExpand(courseId).toUri(), courseSession, CourseSession.class);
    }

    public Optional<CourseSession> findSessionById(Long courseId, Long sessionId) {
        String url = UriComponentsBuilder.fromUriString("http://next-course-service/courses/{courseId}/sessions/{sessionId}")
                .buildAndExpand(courseId, sessionId).toUriString();
        try {
            CourseSession retrievedSession = restTemplate.getForObject(url, CourseSession.class);
            return Optional.ofNullable(retrievedSession);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<CourseSession> findAllSessionsByCourseId(Long courseId) {
        String url = UriComponentsBuilder.fromUriString("http://next-course-service/courses/{courseId}/sessions")
                .buildAndExpand(courseId).toUriString();
        CourseSession[] sessions = restTemplate.getForObject(url, CourseSession[].class);
        if (sessions == null) {
            return Collections.emptyList();
        }

        return Arrays.asList(sessions);
    }

    public CourseRating addRatingToCourse(Long userId, Long courseId, Integer rating, String comment) {
        CourseRating courseRating = new CourseRating();
        courseRating.setUserId(userId);
        courseRating.setCourseId(courseId);
        courseRating.setRating(rating);
        courseRating.setComment(comment);

        String url = UriComponentsBuilder.fromUriString("http://next-course-service/courses/{courseId}/ratings")
                .buildAndExpand(courseId).toUriString();
        return restTemplate.postForObject(url, courseRating, CourseRating.class);
    }
}
