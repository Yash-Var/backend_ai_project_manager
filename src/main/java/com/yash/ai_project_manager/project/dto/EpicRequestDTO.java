package com.yash.ai_project_manager.project.dto;

import java.util.UUID;

public record EpicRequestDTO(

        String name,

        String description,

        UUID projectId

) {
}
