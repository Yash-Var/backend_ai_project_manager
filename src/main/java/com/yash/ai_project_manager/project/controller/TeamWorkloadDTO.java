package com.yash.ai_project_manager.project.controller;

public record TeamWorkloadDTO(

        String developer,

        Integer storyPoints,

        Integer taskCount

) {
}