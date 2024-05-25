package com.fastcampus.nextgraphql.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventLog {
    private Long eventId;
    private Long recordId;
    private Long userId;
    private String eventType;
    private String timestamp;
}
