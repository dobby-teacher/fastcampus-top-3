### 1. 강의 관리 서비스 설계
#### 강의 생성 (POST)
- `/courses`
    - 새로운 강의를 생성합니다. 강의 제목, 설명, 강사 정보 등을 포함하는 데이터가 요청 본문에 포함되어야 합니다.

#### 강의 정보 업데이트 (PUT)
- `/courses/{courseId}`
    - 특정 강의의 정보를 업데이트합니다. 제목, 설명, 강사 정보 등을 변경할 수 있습니다.

#### 강의 정보 조회 (GET)
- `/courses/{courseId}`
    - 특정 강의의 정보를 조회합니다.

#### 모든 강의 목록 조회 (GET)
- `/courses`
    - 시스템에 등록된 모든 강의의 목록을 조회합니다.

### 2. 강의 세션 관리

#### 강의 세션 추가 (POST)
- `/courses/{courseId}/sessions`
    - 특정 강의에 세션을 추가합니다. 세션 제목, 비디오 URL, 지속 시간을 요청 본문에 포함시킵니다.

#### 강의 세션 정보 업데이트 (PUT)
- `/courses/{courseId}/sessions/{sessionId}`
    - 특정 강의의 특정 세션 정보를 업데이트합니다.

#### 강의 세션 정보 조회 (GET)
- `/courses/{courseId}/sessions/{sessionId}`
    - 특정 강의의 특정 세션 정보를 조회합니다.

#### 강의 모든 세션 목록 조회 (GET)
- `/courses/{courseId}/sessions`
    - 특정 강의의 모든 세션 정보를 조회합니다.

### 3. 강의 평가

#### 강의 평가 추가 (POST)
- `/courses/{courseId}/ratings`
    - 특정 강의에 대한 사용자 평가를 추가합니다. 평점과 코멘트를 요청 본문에 포함시킵니다.

#### 강의 평가 업데이트 (PUT)
- `/courses/{courseId}/ratings/{ratingId}`
    - 특정 강의의 특정 평가를 업데이트합니다.

#### 강의 평가 삭제 (DELETE)
- `/courses/{courseId}/ratings/{ratingId}`
    - 특정 강의의 특정 평가를 삭제합니다.

#### 모든 강의 평가 조회 (GET)
- `/courses/{courseId}/ratings`
    - 특정 강의에 대한 모든 평가를 조회합니다.

### 데이터베이스 구조
- DB명 : next_course
- Database per Service 패턴 적용
- 강의 관련 테이블만 DB 및 테이블 생성
  - courses
  - course_session
  - course_ratings