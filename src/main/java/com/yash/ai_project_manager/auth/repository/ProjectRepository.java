package com.yash.ai_project_manager.auth.repository;

import com.yash.ai_project_manager.auth.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectRepository
        extends JpaRepository<Project, UUID> {
}
