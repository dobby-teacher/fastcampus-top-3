package com.fastcampus.nextfilemanageservice.domain.controller;

import com.fastcampus.nextfilemanageservice.domain.entity.SessionFile;
import com.fastcampus.nextfilemanageservice.domain.service.FileStorageService;
import com.fastcampus.nextfilemanageservice.domain.service.SessionFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/sessions/{sessionId}/files")
@RequiredArgsConstructor
public class SessionFileController {

    private final SessionFileService sessionFileService;
    private final FileStorageService fileStorageService;

    @GetMapping
    @Operation(summary = "최근 세션 파일 조회", description = "지정된 세션의 가장 최근 파일을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 파일을 조회함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SessionFile.class))),
            @ApiResponse(responseCode = "404", description = "파일을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<SessionFile> getRecentFileBySessionId(
            @Parameter(description = "파일을 조회할 세션의 ID", required = true)
            @PathVariable Long sessionId) {
        return sessionFileService.findTopBySessionIdOrderByFileIdDesc(sessionId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{fileId}")
    @Operation(summary = "파일 ID로 조회", description = "파일 ID로 세션 파일을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 파일을 조회함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SessionFile.class))),
            @ApiResponse(responseCode = "404", description = "파일을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<SessionFile> getFileById(
            @Parameter(description = "조회할 파일의 ID", required = true)
            @PathVariable Long fileId) {
        return sessionFileService.findFileById(fileId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "세션에 파일 업로드", description = "지정된 세션에 파일을 업로드합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 파일이 업로드됨", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SessionFile.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 입력"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<SessionFile> uploadFile(
            @Parameter(description = "파일을 업로드할 세션의 ID", required = true)
            @PathVariable Long sessionId,
            @Parameter(description = "업로드할 파일", required = true)
            @RequestParam("file") MultipartFile file) {
        SessionFile storedFile = fileStorageService.storeFile(file, sessionId);
        return ResponseEntity.ok(sessionFileService.saveFile(storedFile));
    }

    @DeleteMapping("/{fileId}")
    @Operation(summary = "파일 삭제", description = "지정된 ID의 파일을 삭제합니다.", responses = {
            @ApiResponse(responseCode = "204", description = "성공적으로 파일이 삭제됨"),
            @ApiResponse(responseCode = "404", description = "파일을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<Void> deleteFile(
            @Parameter(description = "삭제할 파일의 ID", required = true)
            @PathVariable Long fileId) {
        sessionFileService.deleteFile(fileId);
        return ResponseEntity.noContent().build();
    }
}
