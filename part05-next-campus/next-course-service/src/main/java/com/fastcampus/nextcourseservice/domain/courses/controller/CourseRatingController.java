package com.fastcampus.nextcourseservice.domain.courses.controller;

import com.fastcampus.nextcourseservice.domain.courses.entity.CourseRating;
import com.fastcampus.nextcourseservice.domain.courses.service.CourseRatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses/{courseId}/ratings")
public class CourseRatingController {

    private final CourseRatingService ratingService;

    @Autowired
    public CourseRatingController(CourseRatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    @Operation(summary = "코스에 새로운 평점 추가", description = "지정된 코스에 새로운 평점을 추가합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 평점이 추가됨", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseRating.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 입력"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<CourseRating> addRating(
            @Parameter(description = "평점을 추가할 코스의 ID", required = true)
            @PathVariable Long courseId,
            @Parameter(description = "추가할 평점 객체", required = true)
            @RequestBody CourseRating rating) {
        CourseRating newRating = ratingService.addRatingToCourse(courseId, rating);
        return ResponseEntity.ok(newRating);
    }

    @PutMapping("/{ratingId}")
    @Operation(summary = "기존 평점 수정", description = "기존 평점을 수정합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 평점이 수정됨", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseRating.class))),
            @ApiResponse(responseCode = "404", description = "평점을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<CourseRating> updateRating(
            @Parameter(description = "수정할 평점의 ID", required = true)
            @PathVariable Long ratingId,
            @Parameter(description = "수정된 평점 객체", required = true)
            @RequestBody CourseRating rating) {
        CourseRating updatedRating = ratingService.updateRating(ratingId, rating);
        return ResponseEntity.ok(updatedRating);
    }

    @DeleteMapping("/{ratingId}")
    @Operation(summary = "평점 삭제", description = "지정된 ID의 평점을 삭제합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 평점이 삭제됨"),
            @ApiResponse(responseCode = "404", description = "평점을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<Void> deleteRating(
            @Parameter(description = "삭제할 평점의 ID", required = true)
            @PathVariable Long ratingId) {
        ratingService.deleteRating(ratingId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "코스의 모든 평점 조회", description = "지정된 코스의 모든 평점을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 평점 목록을 조회함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseRating.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<List<CourseRating>> getAllRatings(
            @Parameter(description = "평점을 조회할 코스의 ID", required = true)
            @PathVariable Long courseId) {
        List<CourseRating> ratings = ratingService.getAllRatingsByCourseId(courseId);
        return ResponseEntity.ok(ratings);
    }
}