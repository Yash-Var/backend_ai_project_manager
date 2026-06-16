package com.yash.ai_project_manager.project.dto;

import com.yash.ai_project_manager.project.enums.Role;

public record RegisterRequestDTO(
        String name,
        String email,
        String password,
        Role role
) {}