package com.yash.ai_project_manager.project.controller;

import com.yash.ai_project_manager.project.dto.TaskRequestDTO;
import com.yash.ai_project_manager.project.dto.UpdateTaskStatusRequestDTO;
import com.yash.ai_project_manager.project.entity.ActivityLog;
import com.yash.ai_project_manager.project.entity.Task;
import com.yash.ai_project_manager.project.enums.TaskStatus;
import com.yash.ai_project_manager.project.service.ActivityLogService;
import com.yash.ai_project_manager.project.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final ActivityLogService activityLogService;

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
    @PatchMapping("/{taskId}/status")
    public Task updateTaskStatus(
            @PathVariable UUID taskId,
            @RequestBody UpdateTaskStatusRequestDTO request
    ) {

        return taskService.updateTaskStatus(
                taskId,
                request
        );
    }
    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(
            @PathVariable TaskStatus status
    ) {

        return taskService.getTasksByStatus(
                status
        );
    }
    @GetMapping("/task/{taskId}")
    public List<ActivityLog> getTaskLogs(
            @PathVariable UUID taskId
    ) {
        return activityLogService
                .getTaskLogs(taskId);
    }
}