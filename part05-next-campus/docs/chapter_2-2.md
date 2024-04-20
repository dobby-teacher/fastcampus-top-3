### 1. 사용자 관리 서비스 (User Management Service)
**테이블:** `USERS`
- 사용자 인증 및 프로필 관리

```sql
-- Users Table
CREATE TABLE USERS
(
    user_id       INT AUTO_INCREMENT PRIMARY KEY,
    username      VARCHAR(255) NOT NULL,
    email         VARCHAR(255) UNIQUE NOT NULL,
    password_hash CHAR(60) NOT NULL,
    created_at    DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_login    DATETIME
);
```

### 2. 강의 관리 서비스 (Course Management Service)
**테이블:** `COURSES`, `COURSE_SESSIONS`, `COURSE_RATINGS`
- 강의 및 세션 정보 관리
- 강의 평가 관리

```sql
-- Courses Table
CREATE TABLE COURSES
(
    course_id     INT AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(255) NOT NULL,
    description   TEXT,
    instructor_id INT NOT NULL,
    created_at    DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (instructor_id) REFERENCES USERS (user_id)
);

-- Course Sessions Table
CREATE TABLE COURSE_SESSIONS
(
    session_id INT AUTO_INCREMENT PRIMARY KEY,
    course_id  INT NOT NULL,
    title      VARCHAR(255) NOT NULL,
    video_url  VARCHAR(255) NOT NULL,
    duration   INT NOT NULL,
    FOREIGN KEY (course_id) REFERENCES COURSES (course_id)
);

-- Course_Ratings Table
CREATE TABLE COURSE_RATINGS
(
    rating_id  INT AUTO_INCREMENT PRIMARY KEY,
    course_id  INT NOT NULL,
    user_id    INT NOT NULL,
    rating     TINYINT NOT NULL,
    comment    TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES COURSES (course_id),
    FOREIGN KEY (user_id) REFERENCES USERS (user_id)
);
```

### 3. 강의 등록/권한 서비스 (Enrollment Service)
**테이블:** `ENROLLMENTS`, `ACCESS_TICKETS`
- 사용자의 권한 정보 관리

```sql
-- Enrollments Table
CREATE TABLE ENROLLMENTS
(
    enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id       INT NOT NULL,
    course_id     INT NOT NULL,
    enroll_date   DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES USERS (user_id),
    FOREIGN KEY (course_id) REFERENCES COURSES (course_id)
);

-- Access_Tickets Table
CREATE TABLE ACCESS_TICKETS
(
  ticket_id       INT AUTO_INCREMENT PRIMARY KEY,
  user_id         INT NOT NULL,
  purchase_date   DATETIME DEFAULT CURRENT_TIMESTAMP,
  expiration_date DATETIME NOT NULL,
  FOREIGN KEY (user_id) REFERENCES USERS (user_id)
);
```

### 4. 컨텐츠 파일 관리 서비스 (Content File Management Service)
**테이블:** `CONTENT_FILES`
- 강의 컨텐츠 파일 관리

```sql
-- Content_Files Table
CREATE TABLE CONTENT_FILES
(
    file_id    INT AUTO_INCREMENT PRIMARY KEY,
    session_id INT NOT NULL,
    file_path  VARCHAR(255) NOT NULL,
    file_type  VARCHAR(50) NOT NULL,
    FOREIGN KEY (session_id) REFERENCES SESSIONS (session_id)
);
```

### 5. 재생 서비스 (Playback Service)
**테이블:** `PLAYBACK_SESSIONS`, `PLAYBACK_EVENTS`
- 사용자의 강의 재생 세션 및 이벤트 추적

```sql
-- Playback Sessions Table
CREATE TABLE PLAYBACK_SESSIONS
(
    playback_id   INT AUTO_INCREMENT PRIMARY KEY,
    user_id       INT NOT NULL,
    session_id    INT NOT NULL,
    start_time    DATETIME,
    end_time      DATETIME,
    FOREIGN KEY (user_id) REFERENCES USERS (user_id),
    FOREIGN KEY (session_id) REFERENCES SESSIONS (session_id)
);

-- Playback Events Table
CREATE TABLE PLAYBACK_EVENTS
(
    event_id      INT AUTO_INCREMENT PRIMARY KEY,
    playback_id   INT NOT NULL,
    event_type    VARCHAR(255) NOT NULL,
    event_time    DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (playback_id) REFERENCES PLAYBACK_SESSIONS (playback_id)
);
```