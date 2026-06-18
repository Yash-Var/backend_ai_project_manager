package com.yash.ai_project_manager.project.controller;

import com.yash.ai_project_manager.project.dto.EpicRequestDTO;
import com.yash.ai_project_manager.project.entity.Epic;
import com.yash.ai_project_manager.project.service.EpicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/epics")
@RequiredArgsConstructor
public class EpicController {

    private final EpicService epicService;

    @PostMapping
    public Epic createEpic(
            @RequestBody EpicRequestDTO request
    ) {

        return epicService.createEpic(
                request
        );
    }

    @GetMapping("/project/{projectId}")
    public List<Epic> getProjectEpics(
            @PathVariable UUID projectId
    ) {

        return epicService.getProjectEpics(
                projectId
        );
    }
}