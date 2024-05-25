package com.fastcampus.nextgraphql.service;

import com.fastcampus.nextgraphql.model.EventLog;
import com.fastcampus.nextgraphql.model.PlaybackRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<PlaybackRecord> findAll() {
        return new ArrayList<>(records);
    }

    public PlaybackRecord save(PlaybackRecord record) {
        if (record.getRecordId() == null) {
            record.setRecordId(recordCounter.incrementAndGet());
        }
        records.add(record);
        return record;
    }

    public Optional<PlaybackRecord> findById(Long recordId) {
        return records.stream()
                .filter(record -> record.getRecordId().equals(recordId))
                .findFirst();
    }

    public void delete(Long recordId) {
        records.removeIf(record -> record.getRecordId().equals(recordId));
    }

    public List<EventLog> findEventLogsByRecordId(Long recordId) {
        return eventLogs.stream()
                .filter(log -> log.getRecordId().equals(recordId))
                .collect(Collectors.toList());
    }

    public EventLog logEvent(EventLog log) {
        log.setEventId(eventCounter.incrementAndGet());
        eventLogs.add(log);
        return log;
    }
}
