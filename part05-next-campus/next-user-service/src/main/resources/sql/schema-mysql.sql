CREATE TABLE `users`
(
    `id`            INT AUTO_INCREMENT PRIMARY KEY COMMENT '사용자의 고유 식별자',
    `name`          VARCHAR(100) NOT NULL COMMENT '사용자의 전체 이름',
    `email`         VARCHAR(100) NOT NULL UNIQUE COMMENT '사용자의 이메일 주소, 유니크 인덱스로 중복 방지',
    `password_hash` VARCHAR(255) NOT NULL COMMENT '사용자의 비밀번호 해시, 보안을 위해 해싱된 값 저장',
    `created_at`    DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '레코드 생성 시간',
    `updated_at`    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '레코드 갱신 시간, 갱신될 때마다 시간 자동 업데이트',
    INDEX           `idx_email` (`email`)
) COMMENT='사용자 정보를 저장하는 테이블. 사용자의 이름, 이메일, 비밀번호 해시를 포함한다.';

CREATE TABLE `user_login_histories`
(
    `id`          INT AUTO_INCREMENT PRIMARY KEY COMMENT '로그인 기록의 고유 식별자',
    `user_id`     INT NOT NULL COMMENT '사용자 테이블의 ID를 참조, 외래키',
    `login_time`  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '로그인 시간, 기본값은 현재 시간',
    `ip_address`  VARCHAR(45) NULL COMMENT '로그인한 사용자의 IP 주소, IPv6 주소도 저장 가능',
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) COMMENT='사용자의 로그인 및 로그아웃 시간을 기록하는 테이블. 로그인과 로그아웃의 정확한 시간과 IP 주소를 저장한다.';
