package com.yash.ai_project_manager.project.controller;

import com.yash.ai_project_manager.project.dto.TaskRequestDTO;
import com.yash.ai_project_manager.project.entity.Task;
import com.yash.ai_project_manager.project.security.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public Task createTask(
            @RequestBody TaskRequestDTO request
    ) {

        return taskService.createTask(
                request
        );
    }
    @GetMapping("/project/{projectId}")
    public List<Task> getTasksByProject(
            @PathVariable UUID projectId
    ) {

        return taskService.getTasksByProject(
                projectId
        );
    }
}