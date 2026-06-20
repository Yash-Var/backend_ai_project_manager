package com.yash.ai_project_manager.project.dto;

import java.util.UUID;

public record SprintTaskRecommendationDTO(

        UUID taskId,

        String title,

        Integer storyPoints,

        String requiredSkill,

        String assignee

) {
}