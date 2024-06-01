-- 강의(COURSES) 테이블에 초기 데이터 삽입
INSERT INTO COURSES (course_id, title, description, instructor_id, created_at)
VALUES (100, '자바 프로그래밍', '기초부터 고급까지 자바 프로그래밍을 다루는 강의', 100, '2023-01-01 00:00:00'),
       (200, '파이썬을 활용한 데이터 과학', '데이터 분석 및 머신러닝을 배우는 파이썬 강의', 200, '2023-01-02 00:00:00');

-- 강의 세션(COURSE_SESSIONS) 테이블에 초기 데이터 삽입
INSERT INTO COURSE_SESSIONS (session_id, course_id, title)
VALUES (100, 100, '자바 소개'),
       (200, 100, '자바에서의 객체지향 프로그래밍'),
       (300, 200, '데이터 과학을 위한 파이썬 기초'),
       (400, 200, '고급 머신러닝 기법');

-- 강의 평가(COURSE_RATINGS) 테이블에 초기 데이터 삽입
INSERT INTO COURSE_RATINGS (rating_id, course_id, user_id, rating, comment, created_at)
VALUES (100, 100, 100, 5, '훌륭한 강의입니다!', '2023-01-03 12:00:00'),
       (200, 100, 101, 4, '아주 좋았지만 조금 어려웠어요', '2023-01-04 12:00:00'),
       (300, 200, 102, 5, '초보자에게 최고의 강의', '2023-01-05 12:00:00'),
       (400, 200, 103, 3, '내용은 좋지만 구성이 조금 아쉽네요', '2023-01-06 12:00:00');
