package com.yash.ai_project_manager.project.dto;

public record AIStandupResponseDTO(

        String prompt,

        String standup,

        Integer riskScore,

        Integer completedTasks,

        Integer inProgressTasks,

        Integer todoTasks

) {
}