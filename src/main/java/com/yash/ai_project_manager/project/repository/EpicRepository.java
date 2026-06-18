package com.yash.ai_project_manager.project.repository;

import com.yash.ai_project_manager.project.entity.Epic;
import com.yash.ai_project_manager.project.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EpicRepository
        extends JpaRepository<Epic, UUID> {

    List<Epic> findByProjectId(
            UUID projectId
    );
}