package com.yash.ai_project_manager.project.service;

import com.yash.ai_project_manager.project.dto.RiskAnalysisResponseDTO;
import com.yash.ai_project_manager.project.entity.Task;
import com.yash.ai_project_manager.project.enums.TaskStatus;
import com.yash.ai_project_manager.project.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AIRiskService {

    private final TaskRepository taskRepository;

    public RiskAnalysisResponseDTO analyze(
            UUID projectId
    ) {

        List<Task> tasks =
                taskRepository.findByProjectId(
                        projectId
                );

        List<String> highRiskTasks =
                new ArrayList<>();

        int riskScore = 0;

        for(Task task : tasks) {

            long age =
                    ChronoUnit.DAYS.between(
                            task.getCreatedAt(),
                            LocalDateTime.now()
                    );

            System.out.println(task.getCreatedAt());
            if(task.getStatus()
                    != TaskStatus.DONE
                    && age > 7) {

                highRiskTasks.add(
                        task.getTitle()
                );

                riskScore += 20;
            }
        }

        if(riskScore > 100) {
            riskScore = 100;
        }

        return new RiskAnalysisResponseDTO(
                riskScore,
                highRiskTasks
        );
    }
}