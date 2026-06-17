package com.yash.ai_project_manager.project.dto;

import java.util.List;

public record SprintPlanResponseDTO(
        String sprintName,
        List<String> tasks,
        Integer totalStoryPoints
) {
}