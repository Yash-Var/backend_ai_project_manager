package com.yash.ai_project_manager.project.repository;

import com.yash.ai_project_manager.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectRepository
        extends JpaRepository<Project, UUID> {
}
