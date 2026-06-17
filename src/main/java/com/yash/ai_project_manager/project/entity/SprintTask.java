package com.yash.ai_project_manager.project.entity;

import com.yash.ai_project_manager.project.entity.Sprint;
import com.yash.ai_project_manager.project.entity.Task;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "sprint_tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SprintTask {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
}