package com.yash.ai_project_manager.project.dto;

import java.util.List;
import java.util.UUID;

public record ApproveSprintPlanRequestDTO(

        UUID projectId,

       List<SprintPlanResponseDTO>sprints

) {
}