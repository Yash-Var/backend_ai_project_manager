package com.yash.ai_project_manager.project.dto;

import java.util.UUID;

public record ProjectMemberResponseDTO(

        UUID userId,

        String name,

        String email,

        String role,

        String skillSet,

        Integer sprintCapacity

) {
}