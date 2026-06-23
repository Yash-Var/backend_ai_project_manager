package com.yash.ai_project_manager.project.service;

import com.yash.ai_project_manager.project.dto.DashboardResponseDTO;
import com.yash.ai_project_manager.project.entity.Task;
import com.yash.ai_project_manager.project.enums.TaskStatus;
import com.yash.ai_project_manager.project.repository.EpicRepository;
import com.yash.ai_project_manager.project.repository.ProjectMemberRepository;
import com.yash.ai_project_manager.project.repository.SprintRepository;
import com.yash.ai_project_manager.project.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TaskRepository taskRepository;
    private final ProjectMemberRepository memberRepository;
    private final EpicRepository epicRepository;
    private final SprintRepository sprintRepository;

    public DashboardResponseDTO getDashboard(
            UUID projectId
    ) {

        long totalTasks =
                taskRepository.countByProjectId(
                        projectId
                );

        long todoTasks =
                taskRepository
                        .countByProjectIdAndStatus(
                                projectId,
                                TaskStatus.TODO
                        );

        long inProgressTasks =
                taskRepository
                        .countByProjectIdAndStatus(
                                projectId,
                                TaskStatus.IN_PROGRESS
                        );

        long completedTasks =
                taskRepository
                        .countByProjectIdAndStatus(
                                projectId,
                                TaskStatus.DONE
                        );

        int totalStoryPoints =
                taskRepository
                        .findByProjectId(
                                projectId
                        )
                        .stream()
                        .mapToInt(
                                Task::getStoryPoints
                        )
                        .sum();

        int completedStoryPoints =
                taskRepository
                        .findByProjectId(
                                projectId
                        )
                        .stream()
                        .filter(task ->
                                task.getStatus()
                                        == TaskStatus.DONE
                        )
                        .mapToInt(
                                Task::getStoryPoints
                        )
                        .sum();

        int teamMembers =
                memberRepository
                        .findByProjectId(
                                projectId
                        )
                        .size();

        int epics =
                epicRepository
                        .findByProjectId(
                                projectId
                        )
                        .size();

        int sprints =
                sprintRepository
                        .findByProjectId(
                                projectId
                        )
                        .size();

        return new DashboardResponseDTO(

                totalTasks,

                todoTasks,

                inProgressTasks,

                completedTasks,

                totalStoryPoints,

                completedStoryPoints,

                teamMembers,

                epics,

                sprints
        );
    }
}