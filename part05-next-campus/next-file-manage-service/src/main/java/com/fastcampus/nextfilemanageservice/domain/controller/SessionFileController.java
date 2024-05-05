package com.fastcampus.nextfilemanageservice.domain.controller;

import com.fastcampus.nextfilemanageservice.domain.entity.SessionFile;
import com.fastcampus.nextfilemanageservice.domain.service.FileStorageService;
import com.fastcampus.nextfilemanageservice.domain.service.SessionFileService;
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
    public ResponseEntity<SessionFile> getRecentFileBySessionId(@PathVariable Long sessionId) {
        return sessionFileService.findTopBySessionIdOrderByFileIdDesc(sessionId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<SessionFile> getFileById(@PathVariable Long fileId) {
        return sessionFileService.findFileById(fileId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SessionFile> uploadFile(@PathVariable Long sessionId, @RequestParam("file") MultipartFile file) {
        SessionFile storedFile = fileStorageService.storeFile(file, sessionId);
        return ResponseEntity.ok(sessionFileService.saveFile(storedFile));
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long fileId) {
        sessionFileService.deleteFile(fileId);
        return ResponseEntity.noContent().build();
    }
}
