package com.yash.ai_project_manager.project.controller;

import com.yash.ai_project_manager.project.dto.*;
import com.yash.ai_project_manager.project.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {

    private final AISprintPlannerService plannerService;
    private final AIRiskService riskService;
    private final GroqService groqService;
    private final AIBootstrapService aiBootstrapService;
    private final AIStandupService aiStandupService;
    private final AIChatService aiChatService;

    @GetMapping("/sprint-plan/{projectId}")
    public List<SprintPlanResponseDTO> generatePlan(

            @PathVariable UUID projectId,

            @RequestParam Integer capacity
    ) {

        return plannerService.generatePlan(
                projectId,
                capacity
        );
    }
    @GetMapping(
            "/project-risk/{projectId}"
    )
    public RiskAnalysisResponseDTO analyzeRisk(
            @PathVariable UUID projectId
    ) {

        return riskService.analyze(
                projectId
        );
    }

    @PostMapping("/analyze-requirement")
    public String analyzeRequirement(
            @RequestBody RequirementRequestDTO request
    ) {

        return groqService.generateEpics(
                request.requirement()
        );
    }

    @PostMapping("/create-project")
    public AIProjectCreationResponseDTO createProject(
            @RequestBody AIProjectCreationRequestDTO request
    )
    {
        return aiBootstrapService
                .createProject(request);
    }

    @PostMapping("/approve-plan")
    public ApproveSprintPlanResponseDTO
    approveSprintPlan(
            @RequestBody
            ApproveSprintPlanRequestDTO request
    ) {

        return plannerService
                .approveSprintPlan(
                        request
                );
    }

    @GetMapping(
            "/standup/{projectId}"
    )
    public AIStandupResponseDTO
    generateStandup(

            @PathVariable
            UUID projectId
    ) {

        return aiStandupService
                .generateStandup(
                        projectId
                );
    }
    @PostMapping("/chat")
    public AIChatResponseDTO chat(

            @RequestBody
            AIChatRequestDTO request

    ) {

        return aiChatService.askQuestion(
                request
        );
    }
}