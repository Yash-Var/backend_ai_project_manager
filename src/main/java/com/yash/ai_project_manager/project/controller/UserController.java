package com.yash.ai_project_manager.project.controller;

import com.yash.ai_project_manager.project.dto.UserRequest;
import com.yash.ai_project_manager.project.entity.User;
import org.springframework.web.bind.annotation.*;
import com.yash.ai_project_manager.project.service.UserService;

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
            ) {

        return userService.getAllUsers();
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