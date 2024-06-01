INSERT INTO session_files (session_id, file_name, file_type, file_path, created_at, updated_at)
VALUES (100, 'intro_to_python.mp4', 'mp4', '/videos/session1/', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (100, 'advanced_python.mp4', 'mp4', '/videos/session1/', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (200, 'intro_to_java.mp4', 'mp4', '/videos/session2/', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (200, 'java_concurrency.mp4', 'mp4', '/videos/session2/', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (300, 'data_structures.mp4', 'mp4', '/videos/session3/', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (300, 'algorithms.mp4', 'mp4', '/videos/session3/', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
