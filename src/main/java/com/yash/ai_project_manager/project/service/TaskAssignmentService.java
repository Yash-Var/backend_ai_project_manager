package com.yash.ai_project_manager.project.service;

import com.yash.ai_project_manager.project.entity.ProjectMember;
import com.yash.ai_project_manager.project.entity.Task;
import com.yash.ai_project_manager.project.entity.User;
import com.yash.ai_project_manager.project.enums.Role;
import com.yash.ai_project_manager.project.repository.ProjectMemberRepository;
import com.yash.ai_project_manager.project.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskAssignmentService {

    private final ProjectMemberRepository projectMemberRepository;
    private final TaskRepository taskRepository;

    private User findBestDeveloper(
            UUID projectId,
            String requiredSkill
    ) {

        List<ProjectMember> members =
                projectMemberRepository
                        .findByProjectId(projectId);

        /*
         * Step 1
         * Exact Skill Match
         */
        List<User> skillMatchedUsers =
                members.stream()

                        .map(ProjectMember::getUser)

                        .filter(user ->
                                user.getSkillSet() != null
                        )

                        .filter(user ->
                                user.getSkillSet()
                                        .toLowerCase()
                                        .contains(
                                                requiredSkill
                                                        .toLowerCase()
                                        )
                        )

                        .toList();

        if (!skillMatchedUsers.isEmpty()) {

            return skillMatchedUsers.stream()

                    .min(
                            Comparator.comparing(
                                    User::getSprintCapacity
                            )
                    )

                    .orElse(null);
        }

        /*
         * Step 2
         * QA Fallback
         */
        if (
                requiredSkill.equalsIgnoreCase(
                        "Testing"
                )
                        ||
                        requiredSkill.equalsIgnoreCase(
                                "QA"
                        )
                        ||
                        requiredSkill.equalsIgnoreCase(
                                "Automation"
                        )
        ) {

            List<User> qaUsers =
                    members.stream()

                            .map(ProjectMember::getUser)

                            .filter(user ->
                                    user.getRole()
                                            == Role.QA
                            )

                            .toList();

            if (!qaUsers.isEmpty()) {

                return qaUsers.stream()

                        .min(
                                Comparator.comparing(
                                        User::getSprintCapacity
                                )
                        )

                        .orElse(null);
            }
        }

        /*
         * Step 3
         * Developer Fallback
         */
        List<User> developers =
                members.stream()

                        .map(ProjectMember::getUser)

                        .filter(user ->
                                user.getRole()
                                        == Role.DEVELOPER
                        )

                        .toList();

        return developers.stream()

                .min(
                        Comparator.comparing(
                                User::getSprintCapacity
                        )
                )

                .orElse(null);
    }
    public void assignTasks(
            UUID projectId
    ) {

        List<Task> tasks =
                taskRepository.findByProjectId(
                        projectId
                );

        for (Task task : tasks) {

            if (task.getAssignee() != null) {
                continue;
            }

            if (
                    task.getRequiredSkill() == null
                            ||
                            task.getRequiredSkill().isBlank()
            ) {
                continue;
            }

            User assignee =
                    findBestDeveloper(
                            projectId,
                            task.getRequiredSkill()
                    );

            if (assignee != null) {

                task.setAssignee(
                        assignee
                );

                taskRepository.save(
                        task
                );

                System.out.println(
                        "Assigned "
                                + task.getTitle()
                                + " -> "
                                + assignee.getName()
                );
            }
        }
    }
}