package com.fastcampus.nextfilemanageservice.domain.controller;

import com.fastcampus.nextfilemanageservice.domain.entity.SessionFile;
import com.fastcampus.nextfilemanageservice.domain.service.SessionFileService;
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
    public ResponseEntity<?> streamVideo(HttpServletRequest request, @PathVariable Long sessionId) {
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
                    throw new RuntimeException("Could not read file: " + file.getFileName());
                }
            } catch (IOException e) {
                return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
            }
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
