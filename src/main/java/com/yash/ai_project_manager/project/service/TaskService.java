package com.yash.ai_project_manager.project.service;

import com.yash.ai_project_manager.project.dto.TaskRequestDTO;
import com.yash.ai_project_manager.project.dto.TaskUpdateMessageDTO;
import com.yash.ai_project_manager.project.dto.UpdateTaskStatusRequestDTO;
import com.yash.ai_project_manager.project.entity.Epic;
import com.yash.ai_project_manager.project.entity.Project;
import com.yash.ai_project_manager.project.entity.Task;
import com.yash.ai_project_manager.project.entity.User;
import com.yash.ai_project_manager.project.enums.ActivityAction;
import com.yash.ai_project_manager.project.enums.TaskStatus;
import com.yash.ai_project_manager.project.repository.EpicRepository;
import com.yash.ai_project_manager.project.repository.ProjectRepository;
import com.yash.ai_project_manager.project.repository.TaskRepository;
import com.yash.ai_project_manager.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ActivityLogService activityLogService;
    private final WebSocketNotificationService webSocketNotificationService;
    private final EpicRepository epicRepository;

    public Task createTask(
            TaskRequestDTO request
    ) {
        System.out.println(request);
        Project project =
                projectRepository.findById(
                        request.projectId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Project not found"
                        ));

        User assignee = null;

        if (request.assigneeId() != null) {

            assignee =
                    userRepository.findById(
                            request.assigneeId()
                    ).orElseThrow(() ->
                            new RuntimeException(
                                    "User not found"
                            ));
        }
        Epic epic =
                epicRepository.findById(
                                request.epicId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Epic not found"
                                ));

        Task task = new Task();

        task.setTitle(request.title());
        task.setDescription(request.description());

        task.setStoryPoints(
                request.storyPoints()
        );
        task.setSequenceOrder(
                request.sequenceOrder()
        );
        task.setRequiredSkill(
                request.requiredSkill()
        );

        task.setStatus(
                TaskStatus.TODO
        );

        task.setProject(project);
        task.setAssignee(assignee);
        task.setEpic(epic);
        task.setCreatedAt(
                LocalDateTime.now()
        );

        Task savedTask =
                taskRepository.save(task);
        if (assignee != null) {

            activityLogService.log(
                    assignee,
                    savedTask,
                    ActivityAction.TASK_CREATED
            );
        }
        webSocketNotificationService.sendTaskUpdate(
                new TaskUpdateMessageDTO(
                        savedTask.getId().toString(),
                        savedTask.getTitle(),
                        savedTask.getStatus().name()
                )
        );
        return savedTask;
    }

    public List<Task> getTasksByProject(
            UUID projectId
    ) {
        return taskRepository
                .findByProjectIdOrderByCreatedAtAsc(projectId);
    }
    public Task updateTaskStatus(
            UUID taskId,
            UpdateTaskStatusRequestDTO request
    ) {

        Task task = taskRepository
                .findById(taskId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Task not found"
                        ));

        TaskStatus oldStatus =
                task.getStatus();
        task.setStatus(
                request.status()
        );

        Task updatedTask =
                taskRepository.save(task);
        webSocketNotificationService.sendTaskUpdate(
                new TaskUpdateMessageDTO(
                        task.getId().toString(),
                        task.getTitle(),
                        task.getStatus().name()
                )
        );
        if (request.status() == TaskStatus.IN_PROGRESS) {

            activityLogService.log(
                    task.getAssignee(),
                    task,
                    ActivityAction.TASK_STARTED
            );

        }
        else if (request.status() == TaskStatus.DONE) {

            activityLogService.log(
                    task.getAssignee(),
                    task,
                    ActivityAction.TASK_COMPLETED
            );

        }
        else {

            activityLogService.log(
                    task.getAssignee(),
                    task,
                    ActivityAction.TASK_STATUS_CHANGED
            );
        }
        return updatedTask;
    }
    public List<Task> getTasksByStatus(
            TaskStatus status
    ) {
        return taskRepository.findByStatus(
                status
        );
    }
    public List<Task> getTasksByEpic(
            UUID epicId
    ) {

        return taskRepository
                .findByEpicId(
                        epicId
                );
    }
}