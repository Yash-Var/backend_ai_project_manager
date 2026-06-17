package com.yash.ai_project_manager.project.service;

import com.yash.ai_project_manager.project.dto.DashboardResponseDTO;
import com.yash.ai_project_manager.project.enums.TaskStatus;
import com.yash.ai_project_manager.project.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TaskRepository taskRepository;

    public DashboardResponseDTO getDashboard(
            UUID projectId
    ) {

        long total =
                taskRepository.countByProjectId(
                        projectId
                );

        long completed =
                taskRepository
                        .countByProjectIdAndStatus(
                                projectId,
                                TaskStatus.DONE
                        );

        long inProgress =
                taskRepository
                        .countByProjectIdAndStatus(
                                projectId,
                                TaskStatus.IN_PROGRESS
                        );

        long blocked =
                taskRepository
                        .countByProjectIdAndStatus(
                                projectId,
                                TaskStatus.BLOCKED
                        );

        double percentage = 0;

        if(total > 0) {

            percentage =
                    ((double) completed
                            / total)
                            * 100;
        }

        return new DashboardResponseDTO(
                (int) total,
                (int) completed,
                (int) inProgress,
                (int) blocked,
                percentage
        );
    }
}