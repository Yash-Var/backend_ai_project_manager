package com.yash.ai_project_manager.project.service;

import com.yash.ai_project_manager.project.controller.TeamWorkloadDTO;
import com.yash.ai_project_manager.project.entity.Task;
import com.yash.ai_project_manager.project.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TeamWorkloadService {

    private final TaskRepository taskRepository;

    public List<TeamWorkloadDTO>
    getWorkload(
            UUID projectId
    ) {

        List<Task> tasks =
                taskRepository
                        .findByProjectId(
                                projectId
                        );

        Map<String,Integer> points =
                new HashMap<>();

        Map<String,Integer> counts =
                new HashMap<>();

        for(Task task : tasks) {

            if(task.getAssignee() == null) {
                continue;
            }

            String developer =
                    task.getAssignee()
                            .getName();

            points.put(
                    developer,
                    points.getOrDefault(
                            developer,
                            0
                    )
                            +
                            task.getStoryPoints()
            );

            counts.put(
                    developer,
                    counts.getOrDefault(
                            developer,
                            0
                    )
                            +
                            1
            );
        }

        return points.entrySet()

                .stream()

                .map(entry ->

                        new TeamWorkloadDTO(

                                entry.getKey(),

                                entry.getValue(),

                                counts.get(
                                        entry.getKey()
                                )
                        )
                )

                .sorted(
                        Comparator.comparing(
                                TeamWorkloadDTO::storyPoints
                        ).reversed()
                )

                .toList();
    }
}