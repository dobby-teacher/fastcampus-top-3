package com.fastcampus.nextgraphql.controller;

import com.fastcampus.nextgraphql.model.User;
import com.fastcampus.nextgraphql.service.DummyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private final DummyUserService userService;

    @Autowired
    public UserController(DummyUserService userService) {
        this.userService = userService;
    }

    @MutationMapping
    public User createUser(@Argument String name, @Argument String email, @Argument String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPasswordHash(password);
        return userService.saveUser(user);
    }

    @MutationMapping
    public User updateUser(@Argument Long id, @Argument String name, @Argument String email) {
        return userService.updateUser(id, name, email);
    }

    @QueryMapping
    public User getUser(@Argument Long id) {
        return userService.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
}

