package com.yash.ai_project_manager.project.dto;

import java.util.List;
import java.util.UUID;

public record AIProjectCreationResponseDTO(

        UUID projectId,

        String projectName,

        List<String> epics

) {
}