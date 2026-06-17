package com.yash.ai_project_manager.project.dto;

import java.time.LocalDate;
import java.util.UUID;

public record SprintRequestDTO(
        String name,
        String goal,
        LocalDate startDate,
        LocalDate endDate,
        UUID projectId
) {
}