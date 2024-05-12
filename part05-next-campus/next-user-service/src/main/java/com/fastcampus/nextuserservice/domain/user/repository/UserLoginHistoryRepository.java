package com.fastcampus.nextuserservice.domain.user.repository;

import com.fastcampus.nextuserservice.domain.user.entity.UserLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, Integer> {
}
