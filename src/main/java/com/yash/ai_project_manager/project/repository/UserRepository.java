package com.yash.ai_project_manager.project.repository;

import com.yash.ai_project_manager.project.entity.User;
import com.yash.ai_project_manager.project.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
        Optional<User> findByEmail(String email);
        List<User> findByRole(
                Role role
        );
}