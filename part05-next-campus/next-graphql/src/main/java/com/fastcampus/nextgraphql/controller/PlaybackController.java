package com.fastcampus.nextgraphql.controller;

import com.fastcampus.nextgraphql.model.EventLog;
import com.fastcampus.nextgraphql.model.PlaybackRecord;
import com.fastcampus.nextgraphql.service.DummyPlaybackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PlaybackController {
    private final DummyPlaybackService playbackService;

    @Autowired
    public PlaybackController(DummyPlaybackService playbackService) {
        this.playbackService = playbackService;
    }

    @QueryMapping
    public PlaybackRecord getPlaybackRecord(@Argument Long id) {
        return playbackService.findById(id)
                .orElseThrow(() -> new RuntimeException("Playback record not found"));
    }

    @MutationMapping
    public PlaybackRecord recordPlayback(@Argument Long userId, @Argument Long fileId,
                                         @Argument String startTime, @Argument String endTime) {
        PlaybackRecord record = new PlaybackRecord(null, userId, fileId, startTime, endTime);
        return playbackService.save(record);
    }

    @QueryMapping
    public List<EventLog> listEventLogs(@Argument Long recordId) {
        return playbackService.findEventLogsByRecordId(recordId);
    }

    @MutationMapping
    public EventLog logEvent(@Argument Long recordId, @Argument Long userId,
                             @Argument String eventType, @Argument String timestamp) {
        EventLog eventLog = new EventLog(null, recordId, userId, eventType, timestamp);
        return playbackService.logEvent(eventLog);
    }
}

