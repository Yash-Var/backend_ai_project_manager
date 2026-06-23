package com.yash.ai_project_manager.project.controller;


import com.yash.ai_project_manager.project.dto.DashboardResponseDTO;
import com.yash.ai_project_manager.project.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/{projectId}")
    public DashboardResponseDTO getDashboard(
            @PathVariable UUID projectId
    ) {

        return dashboardService
                .getDashboard(
                        projectId
                );
    }
}