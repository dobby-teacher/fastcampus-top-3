CREATE TABLE session_files
(
    file_id    INT AUTO_INCREMENT PRIMARY KEY,
    session_id INT NOT NULL,
    file_name  VARCHAR(255) NOT NULL,
    file_type  VARCHAR(50) NOT NULL,
    file_path  VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);