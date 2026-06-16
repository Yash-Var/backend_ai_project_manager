package com.yash.ai_project_manager.project.security;

import com.yash.ai_project_manager.project.dto.TaskRequestDTO;
import com.yash.ai_project_manager.project.entity.Project;
import com.yash.ai_project_manager.project.entity.Task;
import com.yash.ai_project_manager.project.entity.User;
import com.yash.ai_project_manager.project.enums.TaskStatus;
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

    public Task createTask(
            TaskRequestDTO request
    ) {

        Project project =
                projectRepository.findById(
                        request.projectId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Project not found"
                        ));

        User assignee =
                userRepository.findById(
                        request.assigneeId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        ));

        Task task = new Task();

        task.setTitle(request.title());
        task.setDescription(request.description());

        task.setStoryPoints(
                request.storyPoints()
        );

        task.setStatus(
                TaskStatus.TODO
        );

        task.setProject(project);
        task.setAssignee(assignee);

        task.setCreatedAt(
                LocalDateTime.now()
        );

        return taskRepository.save(task);
    }

    public List<Task> getTasksByProject(
            UUID projectId
    ) {
        return taskRepository
                .findByProjectId(projectId);
    }
}