package com.yash.ai_project_manager.project.repository;

import com.yash.ai_project_manager.project.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ActivityLogRepository
        extends JpaRepository<ActivityLog, UUID> {

    List<ActivityLog> findByTaskId(UUID taskId);

    List<ActivityLog> findByUserId(UUID userId);
}