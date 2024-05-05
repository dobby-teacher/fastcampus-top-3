### 1. 컨텐츠 재생 정보 서비스 컴포넌트 설계
- gRPC 서비스 설계
```protobuf
// 서비스 정의
service PlaybackService {
    rpc StartRecord(StartRecordRequest) returns (StartRecordResponse);
    rpc EndRecord(EndRecordRequest) returns (EndRecordResponse);
    rpc LogEvent(LogEventRequest) returns (LogEventResponse);
}
```

- gRPC 모델 설계
```protobuf
message PlaybackRecord {
  string record_id = 1;  // 재생 기록 ID
  string user_id = 2;    // 사용자 ID
  string file_id = 3;    // 파일 ID
  int64 start_time = 4;  // 시작 시간 (UNIX 타임스탬프)
  int64 end_time = 5;    // 종료 시간 (UNIX 타임스탬프)
}

// 재생 기록 시작 요청 메시지
message StartRecordRequest {
  string user_id = 1;      // 사용자 ID
  string file_id = 2;      // 파일 ID
}

// 재생 기록 시작 응답 메시지
message StartRecordResponse {
  PlaybackRecord record = 1; // 시작된 재생 기록 정보
}

// 재생 기록 종료 요청 메시지
message EndRecordRequest {
  string record_id = 1;   // 재생 기록 ID
}

// 재생 기록 종료 응답 메시지
message EndRecordResponse {
  PlaybackRecord record = 1; // 종료된 재생 기록 정보
}

// 이벤트 로깅 요청 메시지
message LogEventRequest {
  EventLog event = 1;      // 로깅할 이벤트
}

// 이벤트 로깅 응답 메시지
message LogEventResponse {
  EventLog event = 1;      // 로깅된 이벤트 정보
}
```

### 데이터베이스 구조
- DB명 : next_playback
- Database per Service 패턴 적용
- 재생 파일 관련 테이블 DB 및 테이블 생성
    - playback_records
    - event_logs