INSERT INTO `users` (`id`, `name`, `email`, `password_hash`, `created_at`, `updated_at`)
VALUES
    (100, 'John Doe', 'john.doe@example.com', 'hashed_password_1', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (200, 'Jane Smith', 'jane.smith@example.com', 'hashed_password_2', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (300, 'Alice Johnson', 'alice.johnson@example.com', 'hashed_password_3', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (400, 'Bob Brown', 'bob.brown@example.com', 'hashed_password_4', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO `user_login_histories` (`user_id`, `login_time`, `ip_address`)
VALUES
    (100, CURRENT_TIMESTAMP(), '192.168.1.1'),
    (200, CURRENT_TIMESTAMP(), '192.168.1.2'),
    (300, CURRENT_TIMESTAMP(), '192.168.1.3'),
    (400, CURRENT_TIMESTAMP(), '192.168.1.4');

