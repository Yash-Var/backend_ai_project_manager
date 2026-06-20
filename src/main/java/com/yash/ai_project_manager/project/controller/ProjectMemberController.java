package com.yash.ai_project_manager.project.controller;

import com.yash.ai_project_manager.project.dto.AddProjectMemberRequestDTO;
import com.yash.ai_project_manager.project.dto.ProjectMemberResponseDTO;
import com.yash.ai_project_manager.project.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    @PostMapping("/{projectId}/members")
    public void addMember(
            @PathVariable UUID projectId,
            @RequestBody AddProjectMemberRequestDTO request
    ) {

        projectMemberService.addMember(
                projectId,
                request
        );
    }

    @GetMapping("/{projectId}/members")
    public List<ProjectMemberResponseDTO> getMembers(
            @PathVariable UUID projectId
    ) {

        return projectMemberService.getMembers(
                projectId
        );
    }

}