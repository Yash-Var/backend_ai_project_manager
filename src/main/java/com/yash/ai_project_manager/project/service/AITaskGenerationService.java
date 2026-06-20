package com.yash.ai_project_manager.project.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yash.ai_project_manager.project.dto.*;
import com.yash.ai_project_manager.project.entity.Epic;
import com.yash.ai_project_manager.project.repository.EpicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AITaskGenerationService {

    private final EpicRepository epicRepository;
    private final GroqService groqService;
    private final TaskService taskService;
    private final ObjectMapper objectMapper;

    public List<AIEpicWithTasksDTO> generateTasksForProject(
            UUID projectId
    ) {

        try {

            List<AIEpicWithTasksDTO> result =
                    new ArrayList<>();

            List<Epic> epics =
                    epicRepository.findByProjectId(
                            projectId
                    );

            for (Epic epic : epics) {

                List<AITaskDTO> generatedTasks =
                        new ArrayList<>();

                String aiResponse =
                        groqService.generateTasks(
                                epic.getName()
                        );

                JsonNode root =
                        objectMapper.readTree(
                                aiResponse
                        );

                String content =
                        root.path("choices")
                                .get(0)
                                .path("message")
                                .path("content")
                                .asText();

                content = content
                        .replace("```json", "")
                        .replace("```", "")
                        .trim();

                AITaskResponseDTO response =
                        objectMapper.readValue(
                                content,
                                AITaskResponseDTO.class
                        );

                for (AITaskDTO task : response.tasks()) {

                    TaskRequestDTO taskRequest =
                            new TaskRequestDTO(
                                    task.title(),
                                    task.description(),
                                    task.storyPoints(),
                                    task.requiredSkill(),
                                    projectId,
                                    null,
                                    epic.getId()
                            );

                    taskService.createTask(
                            taskRequest
                    );

                    generatedTasks.add(
                            task
                    );
                }

                result.add(
                        new AIEpicWithTasksDTO(
                                epic.getName(),
                                generatedTasks
                        )
                );
            }

            return result;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to generate tasks",
                    e
            );
        }
    }
}