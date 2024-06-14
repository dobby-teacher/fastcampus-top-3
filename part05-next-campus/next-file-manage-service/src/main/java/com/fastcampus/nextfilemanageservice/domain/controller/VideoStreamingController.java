package com.fastcampus.nextfilemanageservice.domain.controller;

import com.fastcampus.nextfilemanageservice.domain.entity.SessionFile;
import com.fastcampus.nextfilemanageservice.domain.service.SessionFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@RestController
@RequestMapping("/sessions/{sessionId}")
@RequiredArgsConstructor
public class VideoStreamingController {

    private final SessionFileService sessionFileService;

    @GetMapping("/streams")
    @Operation(summary = "세션 비디오 스트리밍", description = "지정된 세션의 비디오 파일을 스트리밍합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 비디오 스트리밍 시작", content = @Content(mediaType = "video/mp4")),
            @ApiResponse(responseCode = "206", description = "부분 비디오 스트리밍 시작", content = @Content(mediaType = "video/mp4")),
            @ApiResponse(responseCode = "404", description = "비디오 파일을 찾을 수 없음"),
            @ApiResponse(responseCode = "416", description = "잘못된 범위 요청"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<?> streamVideo(
            @Parameter(description = "비디오를 스트리밍할 세션의 ID", required = true)
            @PathVariable Long sessionId,
            @Parameter(description = "HTTP 요청 객체", hidden = true)
            HttpServletRequest request) {
        Optional<SessionFile> fileOptional = sessionFileService.findTopBySessionIdOrderByFileIdDesc(sessionId);

        return fileOptional.map(file -> {
            try {
                Path filePath = Paths.get(file.getFilePath());
                Resource video = new UrlResource(filePath.toUri());
                if (video.exists() || video.isReadable()) {

                    FileChannel fileChannel = FileChannel.open(filePath, StandardOpenOption.READ);
                    long fileLength = fileChannel.size();
                    long start = 0;
                    long end = fileLength - 1;

                    String rangeHeader = request.getHeader("Range");
                    if (rangeHeader != null) {
                        String range = rangeHeader.replace("bytes=", "");
                        String[] ranges = range.split("-");
                        start = Long.parseLong(ranges[0]);
                        if (ranges.length > 1) {
                            end = Long.parseLong(ranges[1]);
                        }
                        if (start > fileLength - 1 || end > fileLength - 1) {
                            return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE).build();
                        }
                    }

                    HttpHeaders headers = new HttpHeaders();
                    headers.add(HttpHeaders.CONTENT_TYPE, "video/mp4");
                    if (rangeHeader != null) {
                        headers.add(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileLength);
                        headers.add(HttpHeaders.ACCEPT_RANGES, "bytes");
                        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(end - start + 1));
                    }

                    InputStreamResource resource = new InputStreamResource(Channels.newInputStream(fileChannel.position(start)));
                    return ResponseEntity.status(rangeHeader == null ? HttpStatus.OK : HttpStatus.PARTIAL_CONTENT)
                            .headers(headers)
                            .body(resource);

                } else {
                    throw new RuntimeException("파일을 읽을 수 없습니다: " + file.getFileName());
                }
            } catch (IOException e) {
                return ResponseEntity.internalServerError().body("오류: " + e.getMessage());
            }
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
