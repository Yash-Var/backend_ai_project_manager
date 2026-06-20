package com.yash.ai_project_manager.project.repository;

import com.yash.ai_project_manager.project.entity.SprintTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SprintTaskRepository
        extends JpaRepository<SprintTask, UUID> {

    List<SprintTask> findBySprintId(
            UUID sprintId
    );
    boolean existsByTaskId(
            UUID taskId
    );
}