package com.yash.ai_project_manager.project.dto;

import java.util.UUID;

public record AIChatRequestDTO(

        UUID projectId,

        String question

) {
}