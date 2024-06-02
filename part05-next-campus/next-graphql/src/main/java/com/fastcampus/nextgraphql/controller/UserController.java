package com.fastcampus.nextgraphql.controller;

import com.fastcampus.nextgraphql.model.User;
import com.fastcampus.nextgraphql.service.UserService;
import com.fastcampus.nextgraphql.service.dummy.DummyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @MutationMapping
    public User createUser(@Argument String name, @Argument String email, @Argument String password) {
        return userService.createUser(name, email, password);
    }

    @MutationMapping
    public User updateUser(@Argument Long userId, @Argument String name, @Argument String email) {
        return userService.updateUser(userId, name, email);
    }

    @QueryMapping
    public User getUser(@Argument Long userId) {
        return userService.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }
}

