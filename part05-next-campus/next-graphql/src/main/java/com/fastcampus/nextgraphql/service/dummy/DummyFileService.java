package com.fastcampus.nextgraphql.service.dummy;

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

    public DummyFileService() {
        initData(); // Initialize with dummy data
    }

    private void initData() {
        // Adding some dummy files to the service
        files.add(new CourseSessionFile(counter.incrementAndGet(), 1L, null, "Introduction to GraphQL.mp4", "mp4", "/files/intro_to_graphql.mp4"));
        files.add(new CourseSessionFile(counter.incrementAndGet(), 1L, null,"GraphQL Queries.mp4", "mp4", "/files/graphql_queries.mp4"));
        files.add(new CourseSessionFile(counter.incrementAndGet(), 2L, null,"Advanced GraphQL.mp4", "mp4", "/files/advanced_graphql.mp4"));
        files.add(new CourseSessionFile(counter.incrementAndGet(), 2L, null,"GraphQL Mutation Strategies.mp4", "mp4", "/files/graphql_mutation_strategies.mp4"));
    }

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
                    .filter(file -> file.getCourseSessionId().equals(sessionId))
                    .collect(Collectors.toList());
    }
}
