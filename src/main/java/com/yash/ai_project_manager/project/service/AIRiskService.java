package com.yash.ai_project_manager.project.service;

import com.yash.ai_project_manager.project.dto.RiskAnalysisResponseDTO;
import com.yash.ai_project_manager.project.entity.Task;
import com.yash.ai_project_manager.project.enums.TaskStatus;
import com.yash.ai_project_manager.project.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AIRiskService {

    private final TaskRepository taskRepository;

    public RiskAnalysisResponseDTO analyze(
            UUID projectId
    ) {

        List<Task> tasks =
                taskRepository
                        .findByProjectIdOrderByCreatedAtAsc(
                                projectId
                        );

        List<String> risks =
                new ArrayList<>();

        List<String> recommendations =
                new ArrayList<>();

        int riskScore = 0;

        /*
         * Risk 1
         * Blocked / Old Tasks
         */
        for (Task task : tasks) {

            long age =
                    ChronoUnit.DAYS.between(
                            task.getCreatedAt(),
                            LocalDateTime.now()
                    );

            if (
                    task.getStatus() != TaskStatus.DONE
                            && age > 7
            ) {

                risks.add(
                        task.getTitle()
                                + " is pending for "
                                + age
                                + " days"
                );

                riskScore += 10;
            }
        }

        /*
         * Risk 2
         * Unassigned Tasks
         */
        long unassignedTasks =
                tasks.stream()

                        .filter(task ->
                                task.getAssignee() == null
                        )

                        .count();

        if (unassignedTasks > 0) {

            risks.add(
                    unassignedTasks
                            + " tasks are unassigned"
            );

            recommendations.add(
                    "Assign remaining tasks to team members"
            );

            riskScore += 20;
        }

        /*
         * Risk 3
         * Developer Overload
         */
        Map<String, Integer> workload =
                new HashMap<>();

        for (Task task : tasks) {

            if (
                    task.getAssignee() == null
            ) {
                continue;
            }

            String developer =
                    task.getAssignee()
                            .getName();

            workload.put(
                    developer,

                    workload.getOrDefault(
                            developer,
                            0
                    )
                            + task.getStoryPoints()
            );
        }

        for (
                Map.Entry<String, Integer> entry
                : workload.entrySet()
        ) {

            if (
                    entry.getValue() > 50
            ) {

                risks.add(
                        entry.getKey()
                                + " is overloaded ("
                                + entry.getValue()
                                + " story points)"
                );

                recommendations.add(
                        "Redistribute work from "
                                + entry.getKey()
                );

                riskScore += 20;
            }
        }

        /*
         * Risk 4
         * Too Many TODO Tasks
         */
        long todoTasks =
                tasks.stream()

                        .filter(task ->
                                task.getStatus()
                                        == TaskStatus.TODO
                        )

                        .count();

        if (
                tasks.size() > 0
                        &&
                        todoTasks >
                                (tasks.size() * 0.6)
        ) {

            risks.add(
                    "Most tasks are still in TODO state"
            );

            recommendations.add(
                    "Start execution of high priority tasks"
            );

            riskScore += 15;
        }

        /*
         * Risk 5
         * Low Completion Rate
         */
        long completedTasks =
                tasks.stream()

                        .filter(task ->
                                task.getStatus()
                                        == TaskStatus.DONE
                        )

                        .count();

        double completionRate =

                tasks.isEmpty()
                        ? 0
                        : (
                        completedTasks * 100.0
                ) / tasks.size();

        if (
                completionRate < 30
        ) {

            risks.add(
                    "Project completion rate is low ("
                            + (int) completionRate
                            + "%)"
            );

            recommendations.add(
                    "Focus on completing active work before creating new tasks"
            );

            riskScore += 15;
        }

        /*
         * Positive Recommendations
         */
        if (
                riskScore < 30
        ) {

            recommendations.add(
                    "Project is progressing well"
            );

            recommendations.add(
                    "Current workload distribution looks healthy"
            );
        }

        /*
         * Cap Risk Score
         */
        riskScore =
                Math.min(
                        riskScore,
                        100
                );

        return new RiskAnalysisResponseDTO(

                riskScore,

                risks,

                recommendations
        );
    }
}