### 1. 컨텐츠 파일 서비스 컴포넌트 설계

#### **파일 업로드 (File Upload)**
- `POST /api/sessions/{sessionId}/files`
  - 특정 세션에 새로운 파일을 업로드합니다. 이때 파일 유형과 경로가 지정됩니다.
- **Payload**:
```json
{
  "file": "binary data",
  "type": "mp4"
}
```

#### **파일 목록 조회 (List Files)**
- `GET /api/sessions/{sessionId}/files`
  - 특정 세션에 속하는 모든 파일의 목록을 조회합니다.
- **Response**:
```json
[
  {
    "fileId": 1,
    "fileName": "session1_video.mp4",
    "fileType": "mp4",
    "filePath": "/files/session1/"
  },
  {
    "fileId": 2,
    "fileName": "session1_additional.mp4",
    "fileType": "mp4",
    "filePath": "/files/session1/"
  }
]
```

3. **파일 상세 조회 (Get File Detail)**
- `GET /api/sessions/{sessionId}/files/{fileId}`
  - 특정 세션의 특정 파일에 대한 상세 정보를 조회합니다.
- **Response**:
```json
{
  "fileId": 1,
  "fileName": "session1_video.mp4",
  "fileType": "mp4",
  "filePath": "/files/session1/",
  "url": "http://example.com/files/session1_video.mp4"
}
```

#### **파일 수정 (Update File)**
- `PUT /api/sessions/{sessionId}/files/{fileId}`
  - 특정 세션의 특정 파일의 정보(파일 이름, 유형, 경로)를 수정합니다.
- **Payload**:
```json
{
  "fileName": "updated_session1_video.mp4",
  "fileType": "mp4",
  "filePath": "/files/session1/"
}
```

#### **파일 삭제 (Delete File)**
- `DELETE /api/sessions/{sessionId}/files/{fileId}`
  - 특정 세션의 특정 파일을 시스템에서 삭제합니다.

### 데이터베이스 구조
- DB명 : next_session_files
- Database per Service 패턴 적용
- 세션 파일 관련 테이블만 DB 및 테이블 생성
    - session_files