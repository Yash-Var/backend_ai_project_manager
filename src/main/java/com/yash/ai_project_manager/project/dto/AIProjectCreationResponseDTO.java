package com.yash.ai_project_manager.project.dto;

import java.util.List;
import java.util.UUID;

public record AIProjectCreationResponseDTO(

        UUID projectId,

        String projectName,

        List<AIEpicWithTasksDTO> epics

) {
    public static record AIStandupResponseDTO(

            String standup,

            Integer riskScore,

            Integer completedTasks,

            Integer inProgressTasks,

            Integer todoTasks

    ) {
    }
}