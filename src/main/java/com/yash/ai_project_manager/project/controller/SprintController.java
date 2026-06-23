package com.yash.ai_project_manager.project.controller;

import com.yash.ai_project_manager.project.dto.AssignTaskToSprintRequestDTO;
import com.yash.ai_project_manager.project.dto.SprintRequestDTO;
import com.yash.ai_project_manager.project.dto.SprintSummaryDTO;
import com.yash.ai_project_manager.project.entity.Sprint;
import com.yash.ai_project_manager.project.entity.SprintTask;
import com.yash.ai_project_manager.project.service.SprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sprints")
@RequiredArgsConstructor
public class SprintController {

    private final SprintService sprintService;

    @PostMapping
    public Sprint createSprint(
            @RequestBody SprintRequestDTO request
    ) {

        return sprintService.createSprint(
                request
        );
    }

    @PostMapping("/assign-task")
    public SprintTask assignTask(
            @RequestBody
            AssignTaskToSprintRequestDTO request
    ) {

        return sprintService
                .assignTaskToSprint(
                        request
                );
    }
    @GetMapping("/{sprintId}/tasks")
    public List<SprintTask>
    getSprintTasks(
            @PathVariable UUID sprintId
    )
    {
        return sprintService
                .getSprintTasks(
                        sprintId
                );
    }
    @GetMapping("/project/{projectId}")
    public List<SprintSummaryDTO>
    getProjectSprints(
            @PathVariable UUID projectId
    ) {

        return sprintService
                .getProjectSprints(
                        projectId
                );
    }
}