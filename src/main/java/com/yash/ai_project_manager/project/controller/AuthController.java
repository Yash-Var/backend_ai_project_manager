package com.yash.ai_project_manager.project.controller;

import com.yash.ai_project_manager.project.dto.AuthResponseDTO;
import com.yash.ai_project_manager.project.dto.LoginRequestDTO;
import com.yash.ai_project_manager.project.dto.RegisterRequestDTO;
import com.yash.ai_project_manager.project.service.AuthService;
import com.yash.ai_project_manager.project.dto.MessageResponseDTO;
import org.springframework.web.bind.annotation.*;


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
    public MessageResponseDTO  register(
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

    @GetMapping("/verify-email")
    public MessageResponseDTO verifyEmail(
            @RequestParam String token
    ) {
        return authService.verifyEmail(token);
    }
}