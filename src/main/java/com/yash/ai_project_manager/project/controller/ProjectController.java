package com.yash.ai_project_manager.project.controller;

import com.yash.ai_project_manager.project.dto.ProjectRequestDTO;
import com.yash.ai_project_manager.project.entity.Project;
import com.yash.ai_project_manager.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public Project createProject(
            @RequestBody ProjectRequestDTO request
    ) {
        System.out.println("yash varshney");
        return projectService.createProject(
                request
        );
    }

    @GetMapping
    public List<Project> getProjects() {
        return projectService.getAllProjects();
    }
}