-- 결제 정보를 저장하는 테이블
CREATE TABLE payments
(
    payment_id     INT AUTO_INCREMENT PRIMARY KEY,
    user_id        INT                                 NOT NULL,
    payment_type   VARCHAR(50)                         NOT NULL, -- 결제 유형 구분
    amount         DECIMAL(10, 2)                      NOT NULL,
    payment_method VARCHAR(50)                         NOT NULL,
    payment_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- 강의 등록 정보를 저장하는 테이블
CREATE TABLE enrollments
(
    enrollment_id     INT AUTO_INCREMENT PRIMARY KEY,
    user_id           INT       NOT NULL,
    course_id         INT       NOT NULL,
    payment_id        INT       NOT NULL,
    registration_date TIMESTAMP NOT NULL,
    FOREIGN KEY (payment_id) REFERENCES payments (payment_id)
);

-- 사용자의 구독 정보를 저장하는 테이블
CREATE TABLE subscriptions
(
    subscription_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id         INT       NOT NULL,
    payment_id      INT       NOT NULL,
    start_date      TIMESTAMP NOT NULL,
    end_date        TIMESTAMP NOT NULL,
    FOREIGN KEY (payment_id) REFERENCES payments (payment_id)
);