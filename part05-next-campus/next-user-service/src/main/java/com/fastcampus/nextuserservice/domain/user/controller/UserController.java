package com.fastcampus.nextuserservice.domain.user.controller;

import com.fastcampus.nextuserservice.domain.user.dto.PasswordChangeDTO;
import com.fastcampus.nextuserservice.domain.user.dto.UserDTO;
import com.fastcampus.nextuserservice.domain.user.entity.User;
import com.fastcampus.nextuserservice.domain.user.entity.UserLoginHistory;
import com.fastcampus.nextuserservice.domain.user.exception.DuplicateUserException;
import com.fastcampus.nextuserservice.domain.user.exception.UnauthorizedAccessException;
import com.fastcampus.nextuserservice.domain.user.exception.UserNotFoundException;
import com.fastcampus.nextuserservice.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        try {
            User user = userService.createUser(userDTO.getName(), userDTO.getEmail(), userDTO.getPassword());
            return ResponseEntity.ok(user);
        } catch (DuplicateUserException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
        User user = userService.getUserById(userId).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Integer userId, @RequestBody UserDTO userDTO) {
        User updatedUser = userService.updateUser(userId, userDTO.getName(), userDTO.getEmail());
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{userId}/login-histories")
    public ResponseEntity<List<UserLoginHistory>> getUserLoginHistories(@PathVariable Integer userId) {
        List<UserLoginHistory> loginHistories = userService.getUserLoginHistories(userId);
        return ResponseEntity.ok(loginHistories);
    }

    @PostMapping("/{userId}/password-change")
    public ResponseEntity<Void> changePassword(@PathVariable Integer userId, @RequestBody PasswordChangeDTO passwordChangeDTO) {
        try {
            userService.changePassword(userId, passwordChangeDTO.getOldPassword(), passwordChangeDTO.getNewPassword());
            return ResponseEntity.ok().build();
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<String> handleDuplicateUser(DuplicateUserException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<String> handleUnauthorizedAccess(UnauthorizedAccessException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }
}
