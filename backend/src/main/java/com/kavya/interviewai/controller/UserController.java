package com.kavya.interviewai.controller;

import com.kavya.interviewai.dto.UserRequest;
import com.kavya.interviewai.model.User;
import com.kavya.interviewai.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@Valid @RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }
}