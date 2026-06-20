package com.yash.ai_project_manager.project.service;

import com.yash.ai_project_manager.project.dto.*;
import com.yash.ai_project_manager.project.dto.ApproveSprintPlanRequestDTO;
import com.yash.ai_project_manager.project.dto.SprintPlanResponseDTO;
import com.yash.ai_project_manager.project.dto.SprintTaskRecommendationDTO;
import com.yash.ai_project_manager.project.entity.*;
import com.yash.ai_project_manager.project.entity.SprintTask;
import com.yash.ai_project_manager.project.entity.Task;
import com.yash.ai_project_manager.project.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AISprintPlannerService {

    private final TaskRepository taskRepository;
    private final SprintTaskRepository sprintTaskRepository;
    private final EpicRepository epicRepository;
    private final SprintRepository sprintRepository;
    private final ProjectRepository projectRepository;

    public List<SprintPlanResponseDTO> generatePlan(
            UUID projectId,
            Integer capacity
    ) {

        List<SprintPlanResponseDTO> result =
                new ArrayList<>();

        List<SprintTaskRecommendationDTO> currentTasks =
                new ArrayList<>();

        int currentPoints = 0;

        int sprintNumber = 1;

        List<Epic> epics =
                epicRepository.findByProjectId(
                        projectId
                );

        for (Epic epic : epics) {

            List<Task> epicTasks =
                    taskRepository.findByEpicId(
                            epic.getId()
                    );

            epicTasks =
                    epicTasks.stream()

                            .sorted(
                                    Comparator.comparing(
                                            Task::getSequenceOrder
                                    )
                            )

                            .toList();

            for (Task task : epicTasks) {

                if (
                        currentPoints
                                + task.getStoryPoints()
                                > capacity
                ) {

                    result.add(
                            new SprintPlanResponseDTO(
                                    "Sprint " + sprintNumber,
                                    new ArrayList<>(
                                            currentTasks
                                    ),
                                    currentPoints
                            )
                    );

                    currentTasks.clear();

                    currentPoints = 0;

                    sprintNumber++;
                }

                currentTasks.add(
                        new SprintTaskRecommendationDTO(
                                task.getId(),
                                task.getTitle(),
                                task.getStoryPoints(),
                                task.getRequiredSkill(),
                                task.getAssignee() != null
                                        ? task.getAssignee()
                                        .getName()
                                        : "Unassigned"
                        )
                );

                currentPoints +=
                        task.getStoryPoints();
            }
        }

        if (!currentTasks.isEmpty()) {

            result.add(
                    new SprintPlanResponseDTO(
                            "Sprint " + sprintNumber,
                            new ArrayList<>(
                                    currentTasks
                            ),
                            currentPoints
                    )
            );
        }

        return result;
    }

    public ApproveSprintPlanResponseDTO
    approveSprintPlan(
            ApproveSprintPlanRequestDTO request
    ) {

        Project project =
                projectRepository.findById(
                                request.projectId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Project not found"
                                ));

        int totalTasks = 0;

        int sprintNumber = 1;

        for (
                SprintPlanResponseDTO sprintDTO
                : request.sprints()
        ) {

            List<UUID> taskIds =
                    sprintDTO.tasks()
                            .stream()
                            .map(
                                    SprintTaskRecommendationDTO::taskId
                            )
                            .toList();

            List<Task> tasks =
                    taskRepository.findAllById(
                            taskIds
                    );

            Sprint sprint =
                    new Sprint();

            sprint.setName(
                    sprintDTO.sprintName()
            );

            sprint.setGoal(
                    generateGoal(
                            tasks
                    )
            );

            sprint.setProject(
                    project
            );

            sprint.setStartDate(
                    LocalDate.now()
                            .plusDays(
                                    (long)(sprintNumber - 1) * 14
                            )
            );

            sprint.setEndDate(
                    LocalDate.now()
                            .plusDays(
                                    (long)sprintNumber * 14
                            )
            );

            Sprint savedSprint =
                    sprintRepository.save(
                            sprint
                    );

            for (Task task : tasks) {

                SprintTask sprintTask =
                        new SprintTask();

                sprintTask.setSprint(
                        savedSprint
                );

                sprintTask.setTask(
                        task
                );

                sprintTaskRepository.save(
                        sprintTask
                );

                totalTasks++;
            }

            sprintNumber++;
        }

        return new ApproveSprintPlanResponseDTO(
                request.sprints().size(),
                totalTasks
        );
    }
    private String generateGoal(
            List<Task> tasks
    ) {

        return tasks.stream()

                .map(task ->
                        task.getEpic()
                                .getName()
                )

                .distinct()

                .reduce(
                        (a, b) -> a + ", " + b
                )

                .orElse(
                        "Sprint Goal"
                );
    }
}