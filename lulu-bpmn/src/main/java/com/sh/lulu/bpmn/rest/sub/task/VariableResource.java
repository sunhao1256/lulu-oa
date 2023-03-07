package com.sh.lulu.bpmn.rest.sub.task;

import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.AuthorizationException;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping("/task/{id}/variables/{var}")
public class VariableResource {
    private final TaskService taskService;
    private final static String ResourceTypeName = "task";

    @GetMapping("/data")
    ResponseEntity<?> getVariableBinary(@PathVariable("id") String taskId,
                                       @PathVariable("var") String variableName) {

        TypedValue typedValue = getTypedValueForVariable(taskId, variableName, false);
        return new VariableResponseProvider().getResponseForTypedVariable(typedValue, taskId);
    }

    protected TypedValue getTypedValueForVariable(String taskId, String variableName, boolean deserializeValue) {
        TypedValue value = null;
        try {

            value = taskService.getVariableTyped(taskId, variableName, deserializeValue);
        } catch (AuthorizationException e) {
            throw e;
        } catch (ProcessEngineException e) {
            String errorMessage = String.format("Cannot get %s variable %s: %s", ResourceTypeName, variableName, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
        }

        if (value == null) {
            String errorMessage = String.format("%s variable with name %s does not exist", ResourceTypeName, variableName);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }
        return value;
    }
}
