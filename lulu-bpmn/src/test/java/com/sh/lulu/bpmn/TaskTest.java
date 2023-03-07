package com.sh.lulu.bpmn;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.StartFormData;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.VariableMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.ObjectUtils;

import java.util.List;

@SpringBootTest
@EnableJpaRepositories(basePackages = "com.sh.lulu")
@EntityScan(basePackages = "com.sh.lulu")
public class TaskTest {
    private final Logger logger = LoggerFactory.getLogger("taskTest");
    @Autowired
    FormService formService;
    @Autowired
    TaskService taskService;

    @Test
    public void formTest() {

        String taskId = "6aaf326a-b967-11ed-9420-de8214ccd36f";

        TaskFormData taskFormKey = formService.getTaskFormData(taskId);
        Assertions.assertNotNull(taskFormKey);
    }


    @Test
    public void getStartFormData(){
        StartFormData startFormData = formService.getStartFormData("Process_16cf95a:6:9e6d9b3f-b987-11ed-9c4f-6e0aa16db6a0");
        Assertions.assertNotNull(startFormData);
    }
    @Test
    public void taskTest() {
        String taskId = "530d64a6-b804-11ed-a1db-a62c5c288ee3";
        List<Task> demo = taskService.createTaskQuery().taskCandidateUser("demo").list();

        Assertions.assertFalse(ObjectUtils.isEmpty(demo));

    }

    @Test
    public void getStartFormVariables(){

        String processDefinitionId = "Process_custom:1:c0d01cd7-b9a5-11ed-9e98-827b329f8bd0";

        VariableMap startFormVariables = formService.getStartFormVariables(processDefinitionId);


        Assertions.assertNotNull(startFormVariables);
    }

    @Test
    public void listTask(){
        List<Task> list = taskService.createTaskQuery()
                .processInstanceId("320d7e04-bbf5-11ed-b8e6-dadd5c955c43")
                .list();

        TaskFormData taskFormData = formService.getTaskFormData(list.get(0).getId());
        VariableMap taskFormVariables = formService.getTaskFormVariables(list.get(0).getId());


        StartFormData startFormData = formService.getStartFormData(list.get(0).getProcessDefinitionId());
        VariableMap startFormVariables = formService.getStartFormVariables(list.get(0).getProcessDefinitionId());

        Assertions.assertNotNull(list);
    }
}
