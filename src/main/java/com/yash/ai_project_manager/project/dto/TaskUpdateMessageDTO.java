package com.yash.ai_project_manager.project.dto;

public record TaskUpdateMessageDTO(

        String taskId,

        String title,

        String status

) {
}