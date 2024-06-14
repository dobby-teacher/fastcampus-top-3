package com.fastcampus.nextcourseservice.domain.courses.controller;

import com.fastcampus.nextcourseservice.domain.courses.entity.Course;
import com.fastcampus.nextcourseservice.domain.courses.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    @Operation(summary = "새로운 코스 생성", description = "새로운 코스를 생성합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 코스가 생성됨", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 입력"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<Course> createCourse(
            @Parameter(description = "생성할 코스 객체", required = true)
            @RequestBody Course course) {
        Course newCourse = courseService.saveCourse(course);
        return ResponseEntity.ok(newCourse);
    }

    @PutMapping("/{courseId}")
    @Operation(summary = "기존 코스 수정", description = "기존 코스를 수정합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 코스가 수정됨", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class))),
            @ApiResponse(responseCode = "404", description = "코스를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<Course> updateCourse(
            @Parameter(description = "수정할 코스의 ID", required = true)
            @PathVariable Long courseId,
            @Parameter(description = "수정된 코스 객체", required = true)
            @RequestBody Course course) {
        Course updatedCourse = courseService.updateCourse(courseId, course);
        return ResponseEntity.ok(updatedCourse);
    }

    @GetMapping("/{courseId}")
    @Operation(summary = "ID로 코스 조회", description = "ID로 코스를 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 코스를 조회함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class))),
            @ApiResponse(responseCode = "404", description = "코스를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<Course> getCourseById(
            @Parameter(description = "조회할 코스의 ID", required = true)
            @PathVariable Long courseId) {
        Course course = courseService.getCourseById(courseId)
                .orElseThrow(() -> new RuntimeException("ID가 " + courseId + "인 코스를 찾을 수 없습니다."));
        return ResponseEntity.ok(course);
    }

    @GetMapping
    @Operation(summary = "모든 코스 조회 또는 ID로 필터링", description = "모든 코스를 조회하거나 ID로 필터링합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 코스 목록을 조회함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<List<Course>> getAllCourses(
            @Parameter(description = "선택 사항: 필터링할 코스 ID 목록")
            @RequestParam(required = false) List<Long> courseId) {
        List<Course> courses;
        if (courseId == null || courseId.isEmpty()) {
            courses = courseService.getAllCourses();
        } else {
            courses = courseService.getCourseByIds(courseId);
        }

        return ResponseEntity.ok(courses);
    }
}
