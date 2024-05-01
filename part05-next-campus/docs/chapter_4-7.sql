CREATE TABLE session_files
(
    file_id    INT AUTO_INCREMENT PRIMARY KEY COMMENT '파일의 고유 식별자입니다.',
    session_id INT          NOT NULL COMMENT '파일이 속한 세션의 식별자입니다.',
    file_name  VARCHAR(255) NOT NULL COMMENT '저장된 파일의 이름입니다.',
    file_type  VARCHAR(50)  NOT NULL COMMENT '파일의 유형을 나타냅니다 (예: mp4).',
    file_path  VARCHAR(255) NOT NULL COMMENT '서버 상에 파일이 저장된 경로입니다.',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '파일이 처음 생성된 날짜와 시간입니다.',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '파일 정보가 마지막으로 업데이트된 날짜와 시간입니다.'
);

