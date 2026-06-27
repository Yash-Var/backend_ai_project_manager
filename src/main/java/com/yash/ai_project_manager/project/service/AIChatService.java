package com.yash.ai_project_manager.project.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yash.ai_project_manager.project.dto.AIChatRequestDTO;
import com.yash.ai_project_manager.project.dto.AIChatResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIChatService {

    private final AIContextService aiContextService;

    private final GroqService groqService;

    private final ObjectMapper objectMapper;

    public AIChatResponseDTO askQuestion(
            AIChatRequestDTO request
    ) {

        String context =
                aiContextService.buildContext(
                        request.projectId()
                );

        String prompt =
                buildPrompt(
                        context,
                        request.question()
                );

        String groqResponse =
                groqService.generateChat(
                        prompt
                );

        String answer =
                extractAnswer(
                        groqResponse
                );

        return new AIChatResponseDTO(
                answer
        );
    }

    private String buildPrompt(

            String context,

            String question

    ) {

        return """
You are an AI Project Manager.

You understand:

- Project Management
- Scrum
- Agile
- Software Engineering
- Sprint Planning
- Risk Analysis
- Team Workload
- Developer Productivity

Use ONLY the project information below.

If the answer is not available,
say you don't have enough information.

Project Information

%s

User Question

%s

Answer professionally.
"""
                .formatted(
                        context,
                        question
                );
    }

    private String extractAnswer(
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