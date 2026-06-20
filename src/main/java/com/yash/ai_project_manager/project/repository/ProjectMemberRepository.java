package com.yash.ai_project_manager.project.repository;

import com.yash.ai_project_manager.project.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectMemberRepository
        extends JpaRepository<ProjectMember, UUID> {

    List<ProjectMember> findByProjectId(
            UUID projectId
    );
}