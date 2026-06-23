package com.yash.ai_project_manager.project.controller;

import com.yash.ai_project_manager.project.dto.ProjectRequestDTO;
import com.yash.ai_project_manager.project.entity.Project;
import com.yash.ai_project_manager.project.service.ProjectService;
import com.yash.ai_project_manager.project.service.TaskAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final TaskAssignmentService taskAssignmentService;

    @PostMapping
    public Project createProject(
            @RequestBody ProjectRequestDTO request
    ) {
        return projectService.createProject(
                request
        );
    }

    @GetMapping
    public List<Project> getProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping("/{projectId}/auto-assign")
    public void autoAssign(
            @PathVariable UUID projectId
    ) {

        taskAssignmentService
                .assignTasks(
                        projectId
                );
    }
    @GetMapping("/{projectId}")
    public Project getProject(
            @PathVariable UUID projectId
    ) {
        return projectService.getProjectById(
                projectId
        );
    }
}
