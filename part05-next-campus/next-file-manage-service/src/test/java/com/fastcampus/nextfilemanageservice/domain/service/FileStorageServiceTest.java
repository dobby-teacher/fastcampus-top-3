package com.fastcampus.nextfilemanageservice.domain.service;

import com.fastcampus.nextfilemanageservice.domain.entity.SessionFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FileStorageServiceTest {

    @InjectMocks
    private FileStorageService fileStorageService;

    @Test
    public void testStoreFileSuccess() throws IOException {
        // Setup
        Long sessionId = 1L;
        String originalFileName = "test.mp4";
        MockMultipartFile mockFile = new MockMultipartFile("file", originalFileName,
                "video/mp4", new ByteArrayInputStream("some data".getBytes()));
        String uploadDir = "./uploads";
        ReflectionTestUtils.setField(fileStorageService, "uploadDir", uploadDir);

        // Act
        SessionFile storedFile = fileStorageService.storeFile(mockFile, sessionId);

        // Assert
        assertNotNull(storedFile);
        assertEquals(sessionId, storedFile.getSessionId());
        assertTrue(storedFile.getFileName().contains("_" + originalFileName));
        assertTrue(storedFile.getFilePath().contains(storedFile.getFileName()));
        assertEquals("mp4", storedFile.getFileType());
    }

    @Test
    public void testStoreFileThrowsExceptionForInvalidPath() throws IOException {
        // Setup
        Long sessionId = 1L;
        String invalidFileName = "../test.mp4";
        MockMultipartFile mockFile = new MockMultipartFile("file", invalidFileName,
                "video/mp4", new ByteArrayInputStream("some data".getBytes()));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            fileStorageService.storeFile(mockFile, sessionId);
        });

        assertTrue(exception.getMessage().contains("Filename contains invalid path sequence"));
    }
}
