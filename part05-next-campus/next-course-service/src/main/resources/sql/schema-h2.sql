CREATE TABLE COURSES
(
    course_id     INTEGER AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(255) NOT NULL,
    description   TEXT,
    instructor_id INT NOT NULL,
    created_at    DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE COURSE_SESSIONS
(
    session_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    course_id  INT NOT NULL,
    title      VARCHAR(255) NOT NULL,
    FOREIGN KEY (course_id) REFERENCES COURSES(course_id)
);

CREATE TABLE COURSE_RATINGS
(
    rating_id  INTEGER AUTO_INCREMENT PRIMARY KEY,
    course_id  INT NOT NULL,
    user_id    INT NOT NULL,
    rating     INTEGER NOT NULL,
    comment    TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES COURSES(course_id)
);