package com.yash.ai_project_manager.project.dto;

public record AITaskDTO(

        String title,

        String description,

        Integer storyPoints,

        String requiredSkill,
        
        Integer sequenceOrder

) {
}