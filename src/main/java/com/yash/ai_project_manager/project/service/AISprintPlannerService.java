package com.yash.ai_project_manager.project.service;

import com.yash.ai_project_manager.project.dto.AssignTaskToSprintRequestDTO;
import com.yash.ai_project_manager.project.dto.SprintPlanResponseDTO;
import com.yash.ai_project_manager.project.entity.Task;
import com.yash.ai_project_manager.project.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AISprintPlannerService {

    private final TaskRepository taskRepository;

    public List<SprintPlanResponseDTO> generatePlan(
            UUID projectId,
            Integer capacity
    ) {

        List<Task> tasks =
                taskRepository.findByProjectId(
                        projectId
                );

        List<SprintPlanResponseDTO> result =
                new ArrayList<>();

        List<String> currentTasks =
                new ArrayList<>();

        int currentPoints = 0;

        int sprintNumber = 1;

        for (Task task : tasks) {

            if (currentPoints
                    + task.getStoryPoints()
                    > capacity) {

                result.add(
                        new SprintPlanResponseDTO(
                                "Sprint " + sprintNumber,
                                new ArrayList<>(currentTasks),
                                currentPoints
                        )
                );

                currentTasks.clear();

                currentPoints = 0;

                sprintNumber++;
            }

            currentTasks.add(
                    task.getTitle()
            );

            currentPoints +=
                    task.getStoryPoints();
        }

        if (!currentTasks.isEmpty()) {

            result.add(
                    new SprintPlanResponseDTO(
                            "Sprint " + sprintNumber,
                            new ArrayList<>(currentTasks),
                            currentPoints
                    )
            );
        }

        return result;
    }
}