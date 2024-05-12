package com.fastcampus.nextuserservice.domain.user.service;

import com.fastcampus.nextuserservice.domain.user.entity.User;
import com.fastcampus.nextuserservice.domain.user.entity.UserLoginHistory;
import com.fastcampus.nextuserservice.domain.user.repository.UserLoginHistoryRepository;
import com.fastcampus.nextuserservice.domain.user.repository.UserRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserLoginHistoryRepository userLoginHistoryRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserLoginHistoryRepository userLoginHistoryRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userLoginHistoryRepository = userLoginHistoryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createUser(String name, String email, String password) {
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPasswordHash(passwordEncoder.encode(password));
        return userRepository.save(newUser);
    }

    public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getUserByEmail(String userId) {
        return userRepository.findByEmail(userId);
    }

    @Transactional
    public User updateUser(Integer userId, String name, String email) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(name);
        user.setEmail(email);
        return userRepository.save(user);
    }

    public List<UserLoginHistory> getUserLoginHistories(Integer userId) {
        return userRepository.findById(userId)
                             .map(User::getLoginHistories)
                             .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void changePassword(Integer userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            user.setPasswordHash(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Invalid old password");
        }
    }

    @Transactional
    public void logUserLogin(User user, String ipAddress) {
        UserLoginHistory loginHistory = new UserLoginHistory();
        loginHistory.setUser(user);
        loginHistory.setLoginTime(LocalDateTime.now());
        loginHistory.setIpAddress(ipAddress);
        userLoginHistoryRepository.save(loginHistory);
    }
}
