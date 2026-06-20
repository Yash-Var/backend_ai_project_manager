package com.yash.ai_project_manager.project.repository;

import com.yash.ai_project_manager.project.entity.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SprintRepository
        extends JpaRepository<Sprint, UUID> {
    List<Sprint> findByProjectId(
            UUID projectId
    );
}