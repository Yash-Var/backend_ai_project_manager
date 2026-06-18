package com.yash.ai_project_manager.project.service;

import com.yash.ai_project_manager.project.dto.EpicRequestDTO;
import com.yash.ai_project_manager.project.entity.Epic;
import com.yash.ai_project_manager.project.entity.Project;
import com.yash.ai_project_manager.project.entity.Task;
import com.yash.ai_project_manager.project.repository.EpicRepository;
import com.yash.ai_project_manager.project.repository.ProjectRepository;
import com.yash.ai_project_manager.project.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EpicService {

    private final EpicRepository epicRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public Epic createEpic(
            EpicRequestDTO request
    ) {

        Project project =
                projectRepository.findById(
                                request.projectId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Project not found"
                                ));

        Epic epic = new Epic();

        epic.setName(
                request.name()
        );

        epic.setDescription(
                request.description()
        );

        epic.setProject(
                project
        );

        epic.setCreatedAt(
                LocalDateTime.now()
        );

        return epicRepository.save(
                epic
        );
    }

    public List<Epic> getProjectEpics(
            UUID projectId
    ) {

        return epicRepository
                .findByProjectId(
                        projectId
                );
    }
}