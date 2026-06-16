package com.yash.ai_project_manager.auth.dto;

public record LoginRequestDTO(
        String email,
        String password
) {}