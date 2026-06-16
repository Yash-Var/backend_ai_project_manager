package com.yash.ai_project_manager.auth.repository;

import com.yash.ai_project_manager.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
        Optional<User> findByEmail(String email);
}