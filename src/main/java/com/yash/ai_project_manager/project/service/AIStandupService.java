package com.yash.ai_project_manager.project.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yash.ai_project_manager.project.dto.AIStandupResponseDTO;
import com.yash.ai_project_manager.project.dto.RiskAnalysisResponseDTO;
import com.yash.ai_project_manager.project.entity.Task;
import com.yash.ai_project_manager.project.enums.TaskStatus;
import com.yash.ai_project_manager.project.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AIStandupService {

    private final AIContextService aiContextService;

    private final GroqService groqService;

    private final TaskRepository taskRepository;

    private final AIRiskService riskService;

    private final ObjectMapper objectMapper;

    public AIStandupResponseDTO generateStandup(
            UUID projectId
    ) {

        String context =
                aiContextService.buildContext(
                        projectId
                );

        String prompt =
                buildPrompt(
                        context
                );

        String response =
                groqService.generateStandup(
                        prompt
                );

        String standup =
                extractContent(
                        response
                );

        List<Task> tasks =
                taskRepository
                        .findByProjectIdOrderByCreatedAtAsc(
                                projectId
                        );

        int completed =
                (int) tasks.stream()
                        .filter(task ->
                                task.getStatus() == TaskStatus.DONE
                        )
                        .count();

        int inProgress =
                (int) tasks.stream()
                        .filter(task ->
                                task.getStatus() == TaskStatus.IN_PROGRESS
                        )
                        .count();

        int todo =
                (int) tasks.stream()
                        .filter(task ->
                                task.getStatus() == TaskStatus.TODO
                        )
                        .count();

        RiskAnalysisResponseDTO risk =
                riskService.analyze(
                        projectId
                );

        return new AIStandupResponseDTO(

                prompt,

                standup,

                risk.riskScore(),

                completed,

                inProgress,

                todo
        );
    }

    private String buildPrompt(
            String context
    ) {

        return """
You are a Senior Scrum Master.

Generate a professional Daily Standup.

Structure your response exactly like this:

Good Morning Team,

Yesterday
- ...

Today
- ...

Blockers
- ...

Sprint Health
- ...

Recommendations
- ...

Use the project information below.

%s
"""
                .formatted(
                        context
                );
    }

    private String extractContent(
            String response
    ) {

        try {

            JsonNode root =
                    objectMapper.readTree(
                            response
                    );

            return root
                    .get("choices")
                    .get(0)
                    .get("message")
                    .get("content")
                    .asText();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to parse AI response",
                    e
            );
        }
    }

}