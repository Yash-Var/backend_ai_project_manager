package com.yash.ai_project_manager.project.service;

import com.yash.ai_project_manager.project.dto.AssignTaskToSprintRequestDTO;
import com.yash.ai_project_manager.project.dto.SprintRequestDTO;
import com.yash.ai_project_manager.project.dto.SprintSummaryDTO;
import com.yash.ai_project_manager.project.entity.Project;
import com.yash.ai_project_manager.project.entity.Sprint;
import com.yash.ai_project_manager.project.entity.SprintTask;
import com.yash.ai_project_manager.project.entity.Task;
import com.yash.ai_project_manager.project.repository.ProjectRepository;
import com.yash.ai_project_manager.project.repository.SprintRepository;
import com.yash.ai_project_manager.project.repository.SprintTaskRepository;
import com.yash.ai_project_manager.project.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SprintService {

    private final SprintRepository sprintRepository;
    private final ProjectRepository projectRepository;
    private final SprintTaskRepository sprintTaskRepository;
    private final TaskRepository taskRepository;

    public Sprint createSprint(
            SprintRequestDTO request
    ) {

        Project project =
                projectRepository.findById(
                                request.projectId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Project not found"
                                ));

        Sprint sprint = new Sprint();

        sprint.setName(
                request.name()
        );

        sprint.setGoal(
                request.goal()
        );

        sprint.setStartDate(
                request.startDate()
        );

        sprint.setEndDate(
                request.endDate()
        );

        sprint.setProject(project);

        return sprintRepository.save(
                sprint
        );
    }

    public SprintTask assignTaskToSprint(
            AssignTaskToSprintRequestDTO request
    ) {

        Sprint sprint =
                sprintRepository.findById(
                                request.sprintId()
                        )
                        .orElseThrow();

        Task task =
                taskRepository.findById(
                                request.taskId()
                        )
                        .orElseThrow();

        SprintTask sprintTask =
                new SprintTask();

        sprintTask.setSprint(
                sprint
        );

        sprintTask.setTask(
                task
        );

        return sprintTaskRepository.save(
                sprintTask
        );
    }
    public List<SprintTask>
    getSprintTasks(UUID sprintId)
    {
        return sprintTaskRepository
                .findBySprintId(
                        sprintId
                );
    }

    public List<SprintSummaryDTO>
    getProjectSprints(
            UUID projectId
    ) {

        List<Sprint> sprints =
                sprintRepository
                        .findByProjectId(
                                projectId
                        );

        return sprints.stream()

                .map(sprint ->

                        new SprintSummaryDTO(

                                sprint.getId(),

                                sprint.getName(),

                                sprint.getGoal(),

                                sprint.getStartDate(),

                                sprint.getEndDate(),

                                sprintTaskRepository
                                        .findBySprintId(
                                                sprint.getId()
                                        )
                                        .size()
                        )
                )

                .toList();
    }
}