package com.yash.ai_project_manager.project.controller;

import com.yash.ai_project_manager.project.dto.*;
import com.yash.ai_project_manager.project.service.AIBootstrapService;
import com.yash.ai_project_manager.project.service.AIRiskService;
import com.yash.ai_project_manager.project.service.AISprintPlannerService;
import com.yash.ai_project_manager.project.service.GroqService;
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
}