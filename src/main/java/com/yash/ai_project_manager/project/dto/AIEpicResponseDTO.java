package com.yash.ai_project_manager.project.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record AIEpicResponseDTO(

        List<String> epics

) {
}