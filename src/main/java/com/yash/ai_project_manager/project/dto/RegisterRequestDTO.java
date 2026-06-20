package com.yash.ai_project_manager.project.dto;

import com.yash.ai_project_manager.project.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@NotBlank
@Email
public record RegisterRequestDTO(
        String name,
        String email,
        String password,
        Role role,
        String skillSet,
        Integer sprintCapacity
) {}