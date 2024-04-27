package com.fastcampus.nextcourseservice.domain.courses.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.fastcampus.nextcourseservice.domain.courses.entity.Course;
import com.fastcampus.nextcourseservice.domain.courses.entity.CourseRating;
import com.fastcampus.nextcourseservice.domain.courses.repository.CourseRatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class CourseRatingServiceTest {

    @Mock
    private CourseRatingRepository ratingRepository;

    @InjectMocks
    private CourseRatingService courseRatingService;

    @Test
    public void testAddRatingToCourse() {
        CourseRating rating = new CourseRating();
        Course course = new Course(1L);
        rating.setCourse(course);
        when(ratingRepository.save(any(CourseRating.class))).thenReturn(rating);

        CourseRating result = courseRatingService.addRatingToCourse(1L, rating);

        assertNotNull(result);
        verify(ratingRepository).save(rating);
        assertEquals(1L, result.getCourse().getId());
    }

    @Test
    public void testUpdateRating() {
        CourseRating existingRating = new CourseRating();
        existingRating.setId(1L);
        existingRating.setRating(5);
        existingRating.setComment("Great course!");

        CourseRating updatedDetails = new CourseRating();
        updatedDetails.setRating(4);
        updatedDetails.setComment("Good course.");

        when(ratingRepository.findById(1L)).thenReturn(Optional.of(existingRating));
        when(ratingRepository.save(any(CourseRating.class))).thenReturn(updatedDetails);

        CourseRating result = courseRatingService.updateRating(1L, updatedDetails);

        assertNotNull(result);
        verify(ratingRepository).findById(1L);
        verify(ratingRepository).save(existingRating);
        assertEquals(4, result.getRating());
        assertEquals("Good course.", result.getComment());
    }

    @Test
    public void testDeleteRating() {
        doNothing().when(ratingRepository).deleteById(1L);

        courseRatingService.deleteRating(1L);

        verify(ratingRepository).deleteById(1L);
    }

    @Test
    public void testGetAllRatingsByCourseId() {
        List<CourseRating> ratings = Arrays.asList(new CourseRating(), new CourseRating());
        when(ratingRepository.findByCourseId(1L)).thenReturn(ratings);

        List<CourseRating> result = courseRatingService.getAllRatingsByCourseId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(ratingRepository).findByCourseId(1L);
    }

    @Test
    public void testGetRatingById() {
        CourseRating rating = new CourseRating();
        when(ratingRepository.findById(1L)).thenReturn(Optional.of(rating));

        Optional<CourseRating> result = courseRatingService.getRatingById(1L);

        assertTrue(result.isPresent());
        assertSame(rating, result.get());
        verify(ratingRepository).findById(1L);
    }
}
