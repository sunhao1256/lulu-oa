package com.sh.lulu.bpmn.repo.cmd;

import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.cfg.CommandChecker;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.form.CamundaFormRefImpl;
import org.camunda.bpm.engine.impl.form.TaskFormDataImpl;
import org.camunda.bpm.engine.impl.form.handler.TaskFormHandler;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.*;
import org.camunda.bpm.engine.impl.task.TaskDefinition;

import java.io.Serializable;

import static org.camunda.bpm.engine.impl.util.EnsureUtil.ensureNotNull;

public class GetHistoricTaskFormCmd implements Command<TaskFormData>, Serializable {

    private static final long serialVersionUID = 1L;
    protected String taskId;

    public GetHistoricTaskFormCmd(String taskId) {
        this.taskId = taskId;
    }

    public TaskFormData execute(CommandContext commandContext) {
        HistoricTaskInstanceManager historicTaskInstanceManager = commandContext.getHistoricTaskInstanceManager();
        HistoricTaskInstanceEntity historicTaskInstance = historicTaskInstanceManager.findHistoricTaskInstanceById(taskId);
        ensureNotNull("No task found for historic taskId '" + taskId + "'", "task",historicTaskInstance);

        ProcessDefinitionEntity processDefinition = Context
                .getProcessEngineConfiguration()
                .getDeploymentCache()
                .findDeployedProcessDefinitionById(historicTaskInstance.getProcessDefinitionId());

        TaskDefinition taskDefinition = processDefinition.getTaskDefinitions().get(historicTaskInstance.getTaskDefinitionKey());

        TaskFormDataImpl taskFormData = new TaskFormDataImpl();
        if (taskDefinition.getCamundaFormDefinitionKey()==null)
            return taskFormData;
        CamundaFormRefImpl camundaFormRef = new CamundaFormRefImpl(taskDefinition.getCamundaFormDefinitionKey().toString(),
                taskDefinition.getCamundaFormDefinitionBinding());
        camundaFormRef.setVersion(Integer.valueOf(taskDefinition.getCamundaFormDefinitionVersion().toString()));
        taskFormData.setCamundaFormRef(camundaFormRef);
        return taskFormData;
    }

}
