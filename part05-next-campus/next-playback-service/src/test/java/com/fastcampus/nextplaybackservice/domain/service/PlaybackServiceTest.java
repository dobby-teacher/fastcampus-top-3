package com.fastcampus.nextplaybackservice.domain.service;

import com.fastcampus.nextplaybackservice.domain.entity.EventLog;
import com.fastcampus.nextplaybackservice.domain.entity.PlaybackRecord;
import com.fastcampus.nextplaybackservice.domain.repository.EventLogRepository;
import com.fastcampus.nextplaybackservice.domain.repository.PlaybackRecordRepository;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaybackServiceTest {

    @Mock
    private PlaybackRecordRepository playbackRecordRepository;

    @Mock
    private EventLogRepository eventLogRepository;

    @Mock
    private StreamObserver<PlaybackServiceOuterClass.StartRecordResponse> startResponseObserver;

    @Mock
    private StreamObserver<PlaybackServiceOuterClass.EndRecordResponse> endResponseObserver;

    @Mock
    private StreamObserver<PlaybackServiceOuterClass.LogEventResponse> logEventResponseObserver;

    @InjectMocks
    private PlaybackService playbackService;

    @Test
    void testStartRecord() {
        when(playbackRecordRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        PlaybackServiceOuterClass.StartRecordRequest request = PlaybackServiceOuterClass.StartRecordRequest.newBuilder()
                .setUserId(1L)
                .setFileId(1L)
                .build();

        playbackService.startRecord(request, startResponseObserver);

        verify(playbackRecordRepository).save(any(PlaybackRecord.class));
        verify(startResponseObserver).onNext(any());
        verify(startResponseObserver).onCompleted();
    }

    @Test
    void testEndRecord() {
        PlaybackRecord record = new PlaybackRecord();
        record.setRecordId(1L);
        record.setUserId(1L);
        record.setFileId(1L);
        record.setStartTime(LocalDateTime.now());

        when(playbackRecordRepository.findById(anyLong())).thenReturn(Optional.of(record));

        PlaybackServiceOuterClass.EndRecordRequest request = PlaybackServiceOuterClass.EndRecordRequest.newBuilder()
                .setRecordId(1L)
                .build();

        playbackService.endRecord(request, endResponseObserver);

        verify(playbackRecordRepository).save(record);
        verify(endResponseObserver).onNext(any());
        verify(endResponseObserver).onCompleted();
    }

    @Test
    void testLogEvent() {
        PlaybackRecord record = new PlaybackRecord();
        record.setRecordId(1L);
        record.setUserId(1L);
        record.setFileId(1L);
        record.setStartTime(LocalDateTime.now());

        when(playbackRecordRepository.findById(anyLong())).thenReturn(Optional.of(record));

        EventLog expectedEvent = new EventLog();
        expectedEvent.setPlaybackRecord(record);
        expectedEvent.setUserId(1L); // Matching the userId type
        expectedEvent.setEventType("PLAY");
        expectedEvent.setTimestamp(LocalDateTime.now());

        // Mock the save method to return the expected event
        when(eventLogRepository.save(any(EventLog.class))).thenReturn(expectedEvent);


        PlaybackServiceOuterClass.LogEventRequest request = PlaybackServiceOuterClass.LogEventRequest.newBuilder()
                .setEvent(PlaybackServiceOuterClass.EventLog.newBuilder()
                        .setRecordId(1L)
                        .setUserId(1L)
                        .setEventType("PLAY"))
                .build();

        playbackService.logEvent(request, logEventResponseObserver);

        verify(eventLogRepository).save(any(EventLog.class));
        verify(logEventResponseObserver).onNext(any());
        verify(logEventResponseObserver).onCompleted();
    }
}
