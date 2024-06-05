package com.fastcampus.nextgraphql.service;

import com.fastcampus.nextgraphql.model.User;
import com.fastcampus.nextgraphql.service.dto.PasswordChangeDTO;
import com.fastcampus.nextgraphql.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
public class UserService {

    private final RestTemplate restTemplate;
    private static final String USER_SERVICE_URL = "http://next-user-service/users";

    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public User createUser(String name, String email, String password) {
        UserDTO userDTO = new UserDTO(name, email, password);
        return restTemplate.postForObject(USER_SERVICE_URL, userDTO, User.class);
    }

    @Cacheable(value = "user", key = "#userId")
    public Optional<User> findById(Long userId) {
        String url = UriComponentsBuilder.fromHttpUrl(USER_SERVICE_URL)
                                         .path("/{userId}")
                                         .buildAndExpand(userId)
                                         .toUriString();
        User user = restTemplate.getForObject(url, User.class);
        return Optional.ofNullable(user);
    }

    public User updateUser(Long userId, String name, String email) {
        UserDTO userDTO = new UserDTO(name, email, null);
        String url = UriComponentsBuilder.fromHttpUrl(USER_SERVICE_URL)
                                         .path("/{userId}")
                                         .buildAndExpand(userId)
                                         .toUriString();
        restTemplate.put(url, userDTO);
        return new User(userId, name, email);  // Assuming you construct a user from the updated info
    }

    public void changePassword(Integer userId, String oldPassword, String newPassword) {
        PasswordChangeDTO passwordChangeDTO = new PasswordChangeDTO(oldPassword, newPassword);
        String url = UriComponentsBuilder.fromHttpUrl(USER_SERVICE_URL)
                                         .path("/{userId}/password-change")
                                         .buildAndExpand(userId)
                                         .toUriString();
        restTemplate.postForLocation(url, passwordChangeDTO);
    }
}
