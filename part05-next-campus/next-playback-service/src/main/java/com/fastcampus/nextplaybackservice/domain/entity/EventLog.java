package com.fastcampus.nextplaybackservice.domain.entity;

import com.fastcampus.nextplaybackservice.domain.service.PlaybackServiceOuterClass;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@Entity
@Table(name = "event_logs")
public class EventLog {

    @Id
    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @ManyToOne
    @JoinColumn(name = "record_id", nullable = false)
    private PlaybackRecord playbackRecord;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "timestamp", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime timestamp;

    public PlaybackServiceOuterClass.EventLog toProto() {
        PlaybackServiceOuterClass.EventLog.Builder builder = PlaybackServiceOuterClass.EventLog.newBuilder();

        if (this.eventId != null) {
            builder.setEventId(this.eventId);
        }
        if (this.playbackRecord != null && this.playbackRecord.getRecordId() != null) {
            builder.setRecordId(this.playbackRecord.getRecordId());
        }
        if (this.userId != null) {
            builder.setUserId(this.userId);
        }
        if (this.eventType != null) {
            builder.setEventType(this.eventType);
        }
        if (this.timestamp != null) {
            builder.setTimestamp(this.timestamp.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        }

        return builder.build();
    }

}
