CREATE TABLE PLAYBACK_SESSIONS
(
    playback_id   INT AUTO_INCREMENT PRIMARY KEY,
    user_id       INT NOT NULL,
    session_id    INT NOT NULL,
    start_time    DATETIME,
    end_time      DATETIME,
    FOREIGN KEY (user_id) REFERENCES Users (user_id),
    FOREIGN KEY (session_id) REFERENCES Sessions (session_id)
);

CREATE TABLE PLAYBACK_EVENTS
(
    event_id      INT AUTO_INCREMENT PRIMARY KEY,
    playback_id   INT NOT NULL,
    event_type    VARCHAR(255) NOT NULL,
    event_time    DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (playback_id) REFERENCES PLAYBACK_SESSIONS (playback_id)
);

CREATE TABLE USERS
(
    user_id       INT AUTO_INCREMENT PRIMARY KEY,
    username      VARCHAR(255) NOT NULL,
    email         VARCHAR(255) UNIQUE NOT NULL,
    password_hash CHAR(60) NOT NULL, -- Assuming bcrypt hash
    created_at    DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_login    DATETIME
);

CREATE TABLE COURSES
(
    course_id     INT AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(255) NOT NULL,
    description   TEXT,
    instructor_id INT NOT NULL,
    created_at    DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (instructor_id) REFERENCES USERS (user_id)
);

CREATE TABLE COURSE_SESSIONS
(
    session_id INT AUTO_INCREMENT PRIMARY KEY,
    course_id  INT NOT NULL,
    title      VARCHAR(255) NOT NULL,
    video_url  VARCHAR(255) NOT NULL,
    duration   INT NOT NULL,
    FOREIGN KEY (course_id) REFERENCES COURSES (course_id)
);

CREATE TABLE ENROLLMENTS
(
    enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id       INT NOT NULL,
    course_id     INT NOT NULL,
    enroll_date   DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES USERS (user_id),
    FOREIGN KEY (course_id) REFERENCES COURSES (course_id)
);

CREATE TABLE CONTENT_FILES
(
    file_id    INT AUTO_INCREMENT PRIMARY KEY,
    session_id INT NOT NULL,
    file_path  VARCHAR(255) NOT NULL,
    file_type  VARCHAR(50) NOT NULL,
    FOREIGN KEY (session_id) REFERENCES SESSIONS (session_id)
);

CREATE TABLE ACCESS_TICKETS
(
    ticket_id       INT AUTO_INCREMENT PRIMARY KEY,
    user_id         INT NOT NULL,
    purchase_date   DATETIME DEFAULT CURRENT_TIMESTAMP,
    expiration_date DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USERS (user_id)
);

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
