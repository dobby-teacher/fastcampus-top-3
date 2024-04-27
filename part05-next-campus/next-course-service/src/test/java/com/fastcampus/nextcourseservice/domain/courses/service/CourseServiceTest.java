package com.fastcampus.nextcourseservice.domain.courses.service;

import com.fastcampus.nextcourseservice.domain.courses.entity.Course;
import com.fastcampus.nextcourseservice.domain.courses.repository.CourseRepository;
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
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    public void testSaveCourse() {
        Course course = new Course();
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course result = courseService.saveCourse(course);

        assertNotNull(result);
        verify(courseRepository).save(course);
    }

    @Test
    public void testUpdateCourse() {
        Course existingCourse = new Course();
        existingCourse.setId(1L);
        existingCourse.setTitle("Original Title");
        existingCourse.setDescription("Original Description");
        existingCourse.setInstructorId(100L);

        Course updatedDetails = new Course();
        updatedDetails.setTitle("Updated Title");
        updatedDetails.setDescription("Updated Description");
        updatedDetails.setInstructorId(101L);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(any(Course.class))).thenReturn(updatedDetails);

        Course result = courseService.updateCourse(1L, updatedDetails);

        assertNotNull(result);
        verify(courseRepository).findById(1L);
        verify(courseRepository).save(existingCourse);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(101L, result.getInstructorId());
    }

    @Test
    public void testGetCourseById() {
        Course course = new Course();
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Optional<Course> result = courseService.getCourseById(1L);

        assertTrue(result.isPresent());
        assertSame(course, result.get());
        verify(courseRepository).findById(1L);
    }

    @Test
    public void testGetAllCourses() {
        List<Course> courses = Arrays.asList(new Course(), new Course());
        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> result = courseService.getAllCourses();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(courseRepository).findAll();
    }
}
