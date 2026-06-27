package com.yash.ai_project_manager.project.service;

import com.yash.ai_project_manager.project.controller.TeamWorkloadDTO;
import com.yash.ai_project_manager.project.dto.RiskAnalysisResponseDTO;
import com.yash.ai_project_manager.project.entity.Project;
import com.yash.ai_project_manager.project.entity.Sprint;
import com.yash.ai_project_manager.project.entity.Task;
import com.yash.ai_project_manager.project.enums.TaskStatus;
import com.yash.ai_project_manager.project.repository.ActivityLogRepository;
import com.yash.ai_project_manager.project.repository.ProjectRepository;
import com.yash.ai_project_manager.project.repository.SprintRepository;
import com.yash.ai_project_manager.project.repository.TaskRepository;
import com.yash.ai_project_manager.project.service.AIRiskService;
import com.yash.ai_project_manager.project.service.TeamWorkloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AIContextService {

    private final ProjectRepository projectRepository;

    private final TaskRepository taskRepository;

    private final ActivityLogRepository activityLogRepository;

    private final SprintRepository sprintRepository;

    private final AIRiskService riskService;

    private final TeamWorkloadService workloadService;

    public String buildContext(
            UUID projectId
    ){
        Project project =
                projectRepository
                        .findById(projectId)
                        .orElseThrow();
        List<Task> tasks =
                taskRepository
                        .findByProjectIdOrderByCreatedAtAsc(
                                projectId
                        );
        RiskAnalysisResponseDTO risk =
                riskService.analyze(
                        projectId
                );
        List<TeamWorkloadDTO> workload =
                workloadService
                        .getWorkload(
                                projectId
                        );
        List<Sprint> sprints =
                sprintRepository
                        .findByProjectId(
                                projectId
                        );
        long completed =
                tasks.stream()

                        .filter(task ->
                                task.getStatus()
                                        == TaskStatus.DONE
                        )

                        .count();
        long inProgress =
                tasks.stream()

                        .filter(task ->
                                task.getStatus()
                                        == TaskStatus.IN_PROGRESS
                        )

                        .count();
        long todo =
                tasks.stream()

                        .filter(task ->
                                task.getStatus()
                                        == TaskStatus.TODO
                        )

                        .count();
        StringBuilder context =
                new StringBuilder();
        context.append("""
================ PROJECT ================

Project Name:
""");

        context.append(project.getName());

        context.append("""

Description:
""");

        context.append(project.getDescription());

        context.append("\n\n");

        context.append("""
================ TASK SUMMARY ================

Total Tasks:
""");

        context.append(tasks.size());

        context.append("""

Completed:
""");

        context.append(completed);

        context.append("""

In Progress:
""");

        context.append(inProgress);

        context.append("""

Todo:
""");

        context.append(todo);

        context.append("\n\n");

        context.append("""
================ CURRENT WORK ================

""");

        for (Task task : tasks) {

            if (task.getStatus() != TaskStatus.IN_PROGRESS) {
                continue;
            }

            context.append(
                    task.getAssignee() != null
                            ? task.getAssignee().getName()
                            : "Unassigned"
            );

            context.append(" working on ");

            context.append(task.getTitle());

            context.append("\n");
        }

        context.append("\n");

        context.append("""
================ RISKS ================

Risk Score:
""");

        context.append(risk.riskScore());

        context.append("\n\n");

        for (String item : risk.risks()) {

            context.append("• ");

            context.append(item);

            context.append("\n");
        }

        context.append("\n");

        context.append("""
================ TEAM WORKLOAD ================

""");

        for (TeamWorkloadDTO member : workload) {

            context.append(member.developer());

            context.append(" : ");

            context.append(member.storyPoints());

            context.append(" Story Points (");

            context.append(member.taskCount());

            context.append(" Tasks)");

            context.append("\n");
        }

        context.append("\n");

        context.append("""
================ SPRINTS ================

""");

        for (Sprint sprint : sprints) {

            context.append(sprint.getName());

            context.append(" : ");

            context.append(sprint.getGoal());

            context.append("\n");
        }

        context.append("""
================ RECOMMENDATIONS ================

""");

        for (String recommendation : risk.recommendations()) {

            context.append("• ");

            context.append(recommendation);

            context.append("\n");
        }

        context.append("\n");

        return context.toString();

    }
}