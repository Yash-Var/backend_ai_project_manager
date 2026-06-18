package com.yash.ai_project_manager.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yash.ai_project_manager.project.config.GroqConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GroqService {

    private final GroqConfig groqConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String GROQ_URL =
            "https://api.groq.com/openai/v1/chat/completions";

    /**
     * Generate Epics from Requirement
     */
    public String generateEpics(
            String requirement
    ) {

        String prompt = """
                You are a senior software architect.

                Return ONLY valid JSON.

                Do not use markdown.
                Do not use backticks.

                Format:

                {
                  "epics":[
                    "Authentication",
                    "Project Management"
                  ]
                }

                Requirement:
                %s
                """.formatted(requirement);

        return callGroq(prompt);
    }

    /**
     * Generate Tasks for Epic
     */
    public String generateTasks(
            String epicName
    ) {

        String prompt = """
                You are a senior software architect.

                Return ONLY valid JSON.

                Format:

                {
                  "tasks":[
                    "Task 1",
                    "Task 2"
                  ]
                }

                Epic:
                %s
                """.formatted(epicName);

        return callGroq(prompt);
    }

    /**
     * Common Groq Call
     */
    private String callGroq(
            String prompt
    ) {

        try {

            Map<String, Object> requestBody =
                    Map.of(

                            "model",
                            "llama-3.3-70b-versatile",

                            "messages",
                            List.of(
                                    Map.of(
                                            "role",
                                            "user",

                                            "content",
                                            prompt
                                    )
                            ),

                            "temperature",
                            0.2
                    );

            String jsonBody =
                    objectMapper.writeValueAsString(
                            requestBody
                    );

            HttpHeaders headers =
                    new HttpHeaders();

            headers.setContentType(
                    MediaType.APPLICATION_JSON
            );

            headers.setBearerAuth(
                    groqConfig.getApiKey()
            );

            HttpEntity<String> entity =
                    new HttpEntity<>(
                            jsonBody,
                            headers
                    );

            ResponseEntity<String> response =
                    restTemplate.exchange(
                            GROQ_URL,
                            HttpMethod.POST,
                            entity,
                            String.class
                    );

            return response.getBody();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error calling Groq API",
                    e
            );
        }
    }
}