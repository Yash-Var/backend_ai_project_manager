package com.yash.ai_project_manager.project.dto;

import java.util.List;

public record RiskAnalysisResponseDTO(

        Integer riskScore,

        List<String> highRiskTasks

) {
}