package com.yash.ai_project_manager.auth.entity;

import jakarta.persistence.*;
import com.yash.ai_project_manager.auth.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter // Generates all getters automatically
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
