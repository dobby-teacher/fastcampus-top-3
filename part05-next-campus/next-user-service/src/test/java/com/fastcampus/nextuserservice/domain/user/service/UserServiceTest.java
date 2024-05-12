package com.fastcampus.nextuserservice.domain.user.service;

import static org.junit.jupiter.api.Assertions.*;

import com.fastcampus.nextuserservice.domain.user.entity.User;
import com.fastcampus.nextuserservice.domain.user.repository.UserRepository;
import com.fastcampus.nextuserservice.domain.user.repository.UserLoginHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserLoginHistoryRepository userLoginHistoryRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser() {
        String name = "John Doe";
        String email = "john@example.com";
        String password = "password123";

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);

        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User savedUser = userService.createUser(name, email, password);

        assertNotNull(savedUser);
        assertEquals(name, savedUser.getName());
        assertEquals(email, savedUser.getEmail());
        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode(password);
    }

    @Test
    void getUserById_found() {
        Integer userId = 1;
        Optional<User> expectedUser = Optional.of(new User());
        when(userRepository.findById(userId)).thenReturn(expectedUser);

        Optional<User> result = userService.getUserById(userId);

        assertTrue(result.isPresent());
        verify(userRepository).findById(userId);
    }

    @Test
    void updateUser_success() {
        Integer userId = 1;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setName("Original Name");
        existingUser.setEmail("original@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User updatedUser = userService.updateUser(userId, "Updated Name", "updated@example.com");

        assertNotNull(updatedUser);
        assertEquals("Updated Name", updatedUser.getName());
        assertEquals("updated@example.com", updatedUser.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void changePassword_success() {
        Integer userId = 1;
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        User user = new User();
        user.setPasswordHash("encodedOldPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(oldPassword, user.getPasswordHash())).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedNewPassword");
        when(userRepository.save(user)).thenReturn(user);

        assertDoesNotThrow(() -> userService.changePassword(userId, oldPassword, newPassword));
        verify(passwordEncoder).encode(newPassword);
        verify(userRepository).save(user);
    }

    @Test
    void changePassword_failWithWrongOldPassword() {
        Integer userId = 1;
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        User user = new User();
        user.setPasswordHash("encodedOldPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(oldPassword, user.getPasswordHash())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.changePassword(userId, oldPassword, newPassword);
        });

        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }
}
