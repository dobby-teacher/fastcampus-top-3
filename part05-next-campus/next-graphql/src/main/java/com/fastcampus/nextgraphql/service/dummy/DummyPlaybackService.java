package com.fastcampus.nextgraphql.service.dummy;

import com.fastcampus.nextgraphql.model.EventLog;
import com.fastcampus.nextgraphql.model.PlaybackRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class DummyPlaybackService {
    private final List<PlaybackRecord> records = new ArrayList<>();
    private final List<EventLog> eventLogs = new ArrayList<>();
    private final AtomicLong recordCounter = new AtomicLong();
    private final AtomicLong eventCounter = new AtomicLong();

    public DummyPlaybackService() {
        initData();  // Initialize with dummy data
    }

    private void initData() {
        // Adding some dummy playback records
        PlaybackRecord record1 = new PlaybackRecord(recordCounter.incrementAndGet(), 1L, 101L, "2024-01-01T10:00:00Z", "2024-01-01T11:00:00Z");
        PlaybackRecord record2 = new PlaybackRecord(recordCounter.incrementAndGet(), 2L, 102L, "2024-01-02T10:00:00Z", "2024-01-02T11:00:00Z");
        records.add(record1);
        records.add(record2);

        // Adding some dummy event logs
        eventLogs.add(new EventLog(eventCounter.incrementAndGet(), record1.getRecordId(), 1L, "play", "2024-01-01T10:00:00Z"));
        eventLogs.add(new EventLog(eventCounter.incrementAndGet(), record1.getRecordId(), 1L, "pause", "2024-01-01T10:30:00Z"));
        eventLogs.add(new EventLog(eventCounter.incrementAndGet(), record2.getRecordId(), 2L, "play", "2024-01-02T10:00:00Z"));
    }

    public List<PlaybackRecord> findAll() {
        return new ArrayList<>(records);
    }

    public PlaybackRecord startRecord(Long userId, Long fileId) {
        PlaybackRecord record = new PlaybackRecord();
        record.setRecordId(recordCounter.incrementAndGet());
        record.setUserId(userId);
        record.setFileId(fileId);

        records.add(record);
        return record;
    }

    public PlaybackRecord endRecord(Long recordId) {
        PlaybackRecord endRecord = records.stream().filter(record -> record.getRecordId().equals(recordId))
                .findFirst()
                .orElseThrow();
        endRecord.setEndTime(new Date().toString());
        return endRecord;
    }

    public Optional<PlaybackRecord> findById(Long recordId) {
        return records.stream()
                .filter(record -> record.getRecordId().equals(recordId))
                .findFirst();
    }

    public void delete(Long recordId) {
        records.removeIf(record -> record.getRecordId().equals(recordId));
        // Also remove associated event logs
        eventLogs.removeIf(log -> log.getRecordId().equals(recordId));
    }

    public List<EventLog> findEventLogsByRecordId(Long recordId) {
        return eventLogs.stream()
                .filter(log -> log.getRecordId().equals(recordId))
                .collect(Collectors.toList());
    }

    public EventLog logEvent(EventLog log) {
        if (log.getEventId() == null) {
            log.setEventId(eventCounter.incrementAndGet());
        }
        eventLogs.add(log);
        return log;
    }
}
