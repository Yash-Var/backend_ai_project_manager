package com.yash.ai_project_manager.auth.service;

import com.yash.ai_project_manager.auth.dto.ProjectRequestDTO;
import com.yash.ai_project_manager.auth.entity.Project;
import com.yash.ai_project_manager.auth.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project createProject(
            ProjectRequestDTO request
    ) {
        Project project = new Project();

        project.setName(request.name());
        project.setDescription(
                request.description()
        );

        project.setCreatedAt(
                LocalDateTime.now()
        );

        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}