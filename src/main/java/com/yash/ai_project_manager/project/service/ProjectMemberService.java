package com.yash.ai_project_manager.project.service;

import com.yash.ai_project_manager.project.dto.AddProjectMemberRequestDTO;
import com.yash.ai_project_manager.project.dto.ProjectMemberResponseDTO;
import com.yash.ai_project_manager.project.entity.Project;
import com.yash.ai_project_manager.project.entity.ProjectMember;
import com.yash.ai_project_manager.project.entity.User;
import com.yash.ai_project_manager.project.repository.ProjectMemberRepository;
import com.yash.ai_project_manager.project.repository.ProjectRepository;
import com.yash.ai_project_manager.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public void addMember(
            UUID projectId,
            AddProjectMemberRequestDTO request
    ) {

        Project project =
                projectRepository.findById(
                        projectId
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Project not found"
                        ));

        User user =
                userRepository.findById(
                        request.userId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        ));

        ProjectMember projectMember =
                new ProjectMember();

        projectMember.setProject(project);
        projectMember.setUser(user);

        projectMemberRepository.save(
                projectMember
        );
    }

    public List<ProjectMemberResponseDTO> getMembers(
            UUID projectId
    ) {

        return projectMemberRepository
                .findByProjectId(projectId)
                .stream()
                .map(member ->
                        new ProjectMemberResponseDTO(
                                member.getUser().getId(),
                                member.getUser().getName(),
                                member.getUser().getEmail(),
                                member.getUser().getRole().name(),
                                member.getUser().getSkillSet(),
                                member.getUser().getSprintCapacity()
                        )
                )
                .toList();
    }
}