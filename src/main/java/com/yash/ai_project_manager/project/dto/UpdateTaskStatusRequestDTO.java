package com.yash.ai_project_manager.project.dto;

import com.yash.ai_project_manager.project.enums.TaskStatus;

public record UpdateTaskStatusRequestDTO(
        TaskStatus status
) {
}