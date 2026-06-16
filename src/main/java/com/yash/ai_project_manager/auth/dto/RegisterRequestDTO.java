package com.yash.ai_project_manager.auth.dto;

import com.yash.ai_project_manager.auth.enums.Role;

public record RegisterRequestDTO(
        String name,
        String email,
        String password,
        Role role
) {}