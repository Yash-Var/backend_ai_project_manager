package com.yash.ai_project_manager.project.controller;

import com.yash.ai_project_manager.project.dto.AuthResponseDTO;
import com.yash.ai_project_manager.project.dto.LoginRequestDTO;
import com.yash.ai_project_manager.project.dto.RegisterRequestDTO;
import com.yash.ai_project_manager.project.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(
            AuthService authService
    ) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponseDTO register(
            @RequestBody RegisterRequestDTO request
    ) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(
            @RequestBody LoginRequestDTO request
    ) {
        return authService.login(request);
    }
}