package com.fastcampus.nextcourseservice.domain.courses.controller;

import com.fastcampus.nextcourseservice.domain.courses.entity.Course;
import com.fastcampus.nextcourseservice.domain.courses.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
    public void testCreateCourse() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setTitle("Test Course");

        given(courseService.saveCourse(any(Course.class))).willReturn(course);

        mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Test Course\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.title").value("Test Course"));
    }

    @Test
    public void testUpdateCourse() throws Exception {
        Course updatedCourse = new Course();
        updatedCourse.setId(1L);
        updatedCourse.setTitle("Updated Course");

        given(courseService.updateCourse(eq(1L), any(Course.class))).willReturn(updatedCourse);

        mockMvc.perform(put("/courses/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Updated Course\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.title").value("Updated Course"));
    }

    @Test
    public void testGetCourseById() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setTitle("Test Course");

        given(courseService.getCourseById(1L)).willReturn(java.util.Optional.of(course));

        mockMvc.perform(get("/courses/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.title").value("Test Course"));
    }

    @Test
    public void testGetAllCourses() throws Exception {
        List<Course> courses = Arrays.asList(new Course(), new Course());

        given(courseService.getAllCourses()).willReturn(courses);

        mockMvc.perform(get("/courses"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2));
    }
}
