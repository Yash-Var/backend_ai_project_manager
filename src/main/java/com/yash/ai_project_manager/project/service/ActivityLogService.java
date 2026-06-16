package com.yash.ai_project_manager.project.service;

import com.yash.ai_project_manager.project.entity.ActivityLog;
import com.yash.ai_project_manager.project.entity.Task;
import com.yash.ai_project_manager.project.entity.User;
import com.yash.ai_project_manager.project.enums.ActivityAction;
import com.yash.ai_project_manager.project.repository.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivityLogService {

    private final ActivityLogRepository repository;

    public void log(
            User user,
            Task task,
            ActivityAction action
    ) {

        ActivityLog log = new ActivityLog();

        log.setUser(user);
        log.setTask(task);
        log.setAction(action);

        log.setCreatedAt(
                LocalDateTime.now()
        );

        repository.save(log);
    }
    public List<ActivityLog> getTaskLogs(
            UUID taskId
    ) {

        return repository.findByTaskId(taskId);
    }
}