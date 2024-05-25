package com.fastcampus.nextgraphql.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaybackRecord {
    private Long recordId;
    private Long userId;
    private Long fileId;
    private String startTime; // In a real scenario, consider using java.time.Instant or java.time.LocalDateTime
    private String endTime;
}
