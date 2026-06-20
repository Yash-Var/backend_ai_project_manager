package com.yash.ai_project_manager.project.dto;

public record ResetPasswordRequestDTO(
        String token,
        String newPassword,
        String confirmPassword
) {}