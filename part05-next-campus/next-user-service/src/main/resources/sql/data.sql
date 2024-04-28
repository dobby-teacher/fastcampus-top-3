INSERT INTO `users` (`id`, `name`, `email`, `password_hash`, `created_at`, `updated_at`)
VALUES
    (1000, 'John Doe', 'john.doe@example.com', 'hashed_password_1', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (2000, 'Jane Smith', 'jane.smith@example.com', 'hashed_password_2', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (3000, 'Alice Johnson', 'alice.johnson@example.com', 'hashed_password_3', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (4000, 'Bob Brown', 'bob.brown@example.com', 'hashed_password_4', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO `user_login_histories` (`user_id`, `login_time`, `ip_address`)
VALUES
    (1000, CURRENT_TIMESTAMP(), '192.168.1.1'),
    (2000, CURRENT_TIMESTAMP(), '192.168.1.2'),
    (3000, CURRENT_TIMESTAMP(), '192.168.1.3'),
    (4000, CURRENT_TIMESTAMP(), '192.168.1.4');

