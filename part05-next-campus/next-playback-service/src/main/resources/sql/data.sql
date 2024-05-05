INSERT INTO playback_records (record_id, user_id, file_id, start_time, end_time)
VALUES (100, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (200, 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (300, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO event_logs (event_id, record_id, user_id, event_type, timestamp)
VALUES (100, 100, 1, 'play', CURRENT_TIMESTAMP),
       (200, 100, 1, 'pause', CURRENT_TIMESTAMP),
       (300, 100, 1, 'play', CURRENT_TIMESTAMP),
       (400, 100, 1, 'stop', CURRENT_TIMESTAMP),
       (500, 200, 2, 'play', CURRENT_TIMESTAMP),
       (600, 200, 2, 'stop', CURRENT_TIMESTAMP),
       (700, 300, 1, 'play', CURRENT_TIMESTAMP),
       (800, 300, 1, 'pause', CURRENT_TIMESTAMP),
       (900, 300, 1, 'play', CURRENT_TIMESTAMP),
       (1000, 300, 1, 'stop', CURRENT_TIMESTAMP);


