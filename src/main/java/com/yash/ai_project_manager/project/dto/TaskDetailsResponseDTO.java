package com.yash.ai_project_manager.project.dto;

import com.yash.ai_project_manager.project.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDetailsResponseDTO(

        UUID id,

        String title,

        String description,

        TaskStatus status,

        Integer storyPoints,

        String requiredSkill,

        Integer sequenceOrder,

        String projectName,

        String epicName,

        String assigneeName,

        LocalDateTime createdAt

) {
}