package com.yash.ai_project_manager.auth.dto;

public record UserRequest(
        String name,
        String email,
        String password
) {
}