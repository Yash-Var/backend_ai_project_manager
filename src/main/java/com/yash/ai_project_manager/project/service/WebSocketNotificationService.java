package com.yash.ai_project_manager.project.service;


import com.yash.ai_project_manager.project.dto.TaskUpdateMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketNotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendTaskUpdate(
            TaskUpdateMessageDTO message
    ) {

        messagingTemplate.convertAndSend(
                "/topic/tasks",
                message
        );
    }
}