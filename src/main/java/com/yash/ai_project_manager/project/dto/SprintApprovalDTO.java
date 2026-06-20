package com.yash.ai_project_manager.project.dto;

import com.yash.ai_project_manager.project.entity.Task;

import java.util.List;
import java.util.UUID;

public record SprintApprovalDTO(

        String sprintName,

        List<Task> tasks

) {
}