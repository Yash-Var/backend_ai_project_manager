package com.yash.ai_project_manager.project.service;

import com.yash.ai_project_manager.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskAssignmentService {

    private final UserRepository userRepository;

}