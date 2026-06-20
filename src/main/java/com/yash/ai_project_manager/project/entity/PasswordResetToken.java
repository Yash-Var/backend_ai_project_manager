package com.yash.ai_project_manager.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "password_reset_tokens")
@Getter
@Setter
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String token;

    private LocalDateTime expiryTime;

    private boolean used;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}