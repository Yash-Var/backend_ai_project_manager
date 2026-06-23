package com.yash.ai_project_manager.project.dto;

import java.time.LocalDate;
import java.util.UUID;

public record SprintSummaryDTO(

        UUID sprintId,

        String sprintName,

        String goal,

        LocalDate startDate,

        LocalDate endDate,

        Integer taskCount

) {
}