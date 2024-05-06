CREATE TABLE playback_records
(
    record_id  INT    NOT NULL COMMENT '고유한 재생 식별자',
    user_id    INT    NOT NULL COMMENT '사용자 식별자, users 테이블의 user_id 참조',
    file_id    INT    NOT NULL COMMENT '재생 파일 식별자',
    start_time BIGINT NOT NULL COMMENT '재생 시작 시간 (UNIX 타임스탬프, 밀리세컨드 단위)',
    end_time   BIGINT COMMENT '재생 종료 시간 (UNIX 타임스탬프, 밀리세컨드 단위)',
    PRIMARY KEY (record_id)
) COMMENT ='재생 활동 데이터를 저장하는 테이블. 각 레코드는 사용자가 파일을 재생하는 기간 동안의 정보를 포함한다.';

CREATE TABLE event_logs
(
    event_id   INT         NOT NULL COMMENT '고유한 이벤트 로그 식별자',
    record_id  INT         NOT NULL COMMENT '관련 재생 세션 식별자, playback_records 테이블의 record_id 참조',
    user_id    INT         NOT NULL COMMENT '사용자 식별자, users 테이블의 user_id 참조',
    event_type VARCHAR(50) NOT NULL COMMENT '이벤트 유형 (예: "play", "pause", "stop")',
    timestamp  BIGINT      NOT NULL COMMENT '이벤트 발생 시간 (UNIX 타임스탬프, 밀리세컨드 단위)',
    PRIMARY KEY (event_id)
) COMMENT ='이벤트 로그 데이터를 저장하는 테이블. 강의 재생 중 발생하는 다양한 이벤트를 기록한다.';
