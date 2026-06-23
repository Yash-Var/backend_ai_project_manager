package com.yash.ai_project_manager.project.dto;

public record DashboardResponseDTO(

        long totalTasks,

        long todoTasks,

        long inProgressTasks,

        long completedTasks,

        int totalStoryPoints,

        int completedStoryPoints,

        int teamMembers,

        int epics,

        int sprints

) {
}