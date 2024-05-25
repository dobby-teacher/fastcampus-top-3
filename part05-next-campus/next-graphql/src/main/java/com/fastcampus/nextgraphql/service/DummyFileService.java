package com.fastcampus.nextgraphql.service;

import com.fastcampus.nextgraphql.model.CourseSessionFile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class DummyFileService {
    private final List<CourseSessionFile> files = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public List<CourseSessionFile> findAll() {
        return new ArrayList<>(files);
    }

    public CourseSessionFile save(CourseSessionFile file) {
        if (file.getFileId() == null) {
            file.setFileId(counter.incrementAndGet());
        }
        files.add(file);
        return file;
    }

    public Optional<CourseSessionFile> findById(Long fileId) {
        return files.stream()
                .filter(file -> file.getFileId().equals(fileId))
                .findFirst();
    }

    public void delete(Long fileId) {
        files.removeIf(file -> file.getFileId().equals(fileId));
    }

    public List<CourseSessionFile> findFilesBySessionId(Long sessionId) {
        return files.stream()
                .filter(file -> file.getSessionId().equals(sessionId))
                .collect(Collectors.toList());
    }
}
