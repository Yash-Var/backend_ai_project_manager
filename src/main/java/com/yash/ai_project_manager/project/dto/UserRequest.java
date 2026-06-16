package com.yash.ai_project_manager.project.dto;

public record UserRequest(
        String name,
        String email,
        String password
) {
}