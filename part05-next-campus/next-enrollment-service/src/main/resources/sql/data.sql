INSERT INTO payments (payment_id, user_id, payment_type, amount, payment_method, payment_date)
VALUES
    (100, 100, 'COURSE', 200.00, 'CARD', NOW()),
    (101, 101, 'COURSE', 150.00, 'CARD', NOW()),
    (102, 102, 'SUBSCRIPTION', 120.00, 'CARD', NOW()),
    (103, 103, 'SUBSCRIPTION', 180.00, 'CARD', NOW());

INSERT INTO enrollments (enrollment_id, user_id, course_id, payment_id, registration_date)
VALUES
    (100, 100, 100, 100, NOW()),
    (101, 101, 101, 101, NOW());


INSERT INTO subscriptions (subscription_id, user_id, payment_id, start_date, end_date)
VALUES
    (100, 102, 102, '2024-05-01 00:00:00', '2024-11-01 00:00:00'),
    (101, 103, 103, '2024-05-02 00:00:00', '2024-12-01 00:00:00');
