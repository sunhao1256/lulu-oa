package com.sh.lulu.bpmn.rest;

import com.sh.lulu.bpmn.rest.dto.task.TaskDto;
import com.sh.lulu.auth.security.SecurityUtils;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class TaskRestService {
    private final TaskService taskService;

    @GetMapping("/tasks/assignee")
    public List<TaskDto> listTasksAssignee() {
        return taskService.createTaskQuery().taskAssignee(SecurityUtils.getCurrentUsername())
                .list().stream().map(TaskDto::fromEntity).collect(Collectors.toList());
    }

    @GetMapping("/tasks/claimable")
    public List<TaskDto> listTasksClaimable() {
        return taskService.createTaskQuery().taskCandidateUser(SecurityUtils.getCurrentUsername())
                .list().stream().map(TaskDto::fromEntity).collect(Collectors.toList());
    }

}
