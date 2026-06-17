package com.yash.ai_project_manager.project.repository;

import com.yash.ai_project_manager.project.entity.Task;
import com.yash.ai_project_manager.project.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository
        extends JpaRepository<Task, UUID> {
    List<Task> findByProjectId(UUID projectId);

    List<Task> findByAssigneeId(UUID assigneeId);

    List<Task> findByStatus(
            TaskStatus status
    );
    long countByProjectIdAndStatus(
            UUID projectId,
            TaskStatus status
    );
    long countByProjectId(UUID projectId);

}