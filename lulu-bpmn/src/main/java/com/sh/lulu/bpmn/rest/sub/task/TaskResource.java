package com.sh.lulu.bpmn.rest.sub.task;


import com.sh.lulu.bpmn.rest.dto.task.TaskDto;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping("/task/{id}")
public class TaskResource {
    private final TaskService taskService;

    @GetMapping
    TaskDto get(@PathVariable("id") String taskId) {
        Task task = getTaskById(taskId);
        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No matching task with id " + taskId);
        }
        return TaskDto.fromEntity(task);
    }

    protected Task getTaskById(String id) {
        return taskService.createTaskQuery().taskId(id).initializeFormKeys().singleResult();
    }


    @PostMapping("/claim/{userId}")
    public void assign(@PathVariable("id") String taskId,
                       @PathVariable("userId") String userId) {
        Task task = taskService.createTaskQuery()
                .taskCandidateUser(userId).taskId(taskId)
                .singleResult();
        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No matching task with id " + taskId + " candidate user with " + userId);
        }
        taskService.claim(taskId, userId);
    }
}
