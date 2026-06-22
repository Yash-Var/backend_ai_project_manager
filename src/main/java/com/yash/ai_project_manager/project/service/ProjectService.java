package com.yash.ai_project_manager.project.service;

import com.yash.ai_project_manager.project.dto.ProjectRequestDTO;
import com.yash.ai_project_manager.project.entity.Project;
import com.yash.ai_project_manager.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.yash.ai_project_manager.project.entity.User;
import com.yash.ai_project_manager.project.enums.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project createProject(
            ProjectRequestDTO request
    ) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        User currentUser =
                (User) authentication.getPrincipal();

        Role role =
                currentUser.getRole();

        if (role != Role.ADMIN &&
                role != Role.PROJECT_MANAGER) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Only ADMIN and PROJECT_MANAGER can create project"
            );
        }

        Project project = new Project();

        project.setName(request.name());

        project.setDescription(
                request.description()
        );

        project.setCreatedAt(
                LocalDateTime.now()
        );

        project.setOwner(currentUser);

        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}