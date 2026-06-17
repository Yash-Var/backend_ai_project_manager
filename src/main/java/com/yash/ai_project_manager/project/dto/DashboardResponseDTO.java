package com.yash.ai_project_manager.project.dto;

public record DashboardResponseDTO(

        Integer totalTasks,

        Integer completedTasks,

        Integer inProgressTasks,

        Integer blockedTasks,

        Double completionPercentage

) {
}