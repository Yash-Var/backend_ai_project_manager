package com.yash.ai_project_manager.project.dto;

import java.util.UUID;

public record TaskRequestDTO(

        String title,

        String description,

        Integer storyPoints,

        UUID projectId,

        UUID assigneeId,

        UUID epicId
) {
}