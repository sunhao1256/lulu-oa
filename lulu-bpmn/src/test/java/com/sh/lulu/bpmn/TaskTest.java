package com.sh.lulu.bpmn;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TaskTest {
    private final Logger logger = LoggerFactory.getLogger("taskTest");
    @Autowired
    FormService formService;

    @Test
    public void formTest() {
        String taskId = "530d64a6-b804-11ed-a1db-a62c5c288ee3";
        TaskFormData taskFormKey = formService.getTaskFormData(taskId);
        VariableMap taskFormVariables = formService.getTaskFormVariables(taskId);


        Assertions.assertNotNull(taskFormKey);
    }

}
