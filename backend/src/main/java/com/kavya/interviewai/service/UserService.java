package com.kavya.interviewai.service;

import com.kavya.interviewai.dto.UserRequest;
import com.kavya.interviewai.model.User;
import com.kavya.interviewai.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());

        return userRepository.save(user);
    }
}