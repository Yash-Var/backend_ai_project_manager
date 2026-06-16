package com.yash.ai_project_manager.auth.controller;

import com.yash.ai_project_manager.auth.dto.UserRequest;
import com.yash.ai_project_manager.auth.entity.User;
import com.yash.ai_project_manager.auth.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import com.yash.ai_project_manager.auth.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(
            @RequestBody UserRequest request
    ) {
        return userService.createUser(request);
    }

    @GetMapping
    public List<User> getAllUsers(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");

        return userService.getAllUsers(token);
    }

    @GetMapping("/{id}")
    public User getUserById(
            @PathVariable UUID id
    ) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(
            @PathVariable UUID id
    ) {

        userService.deleteUser(id);

        return "User Deleted Successfully";
    }
}