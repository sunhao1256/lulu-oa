package com.sh.lulu.bpmn.repo.cmd;

import org.camunda.bpm.engine.BadUserRequestException;
import org.camunda.bpm.engine.form.FormData;
import org.camunda.bpm.engine.impl.cfg.CommandChecker;
import org.camunda.bpm.engine.impl.cmd.AbstractGetDeployedFormCmd;
import org.camunda.bpm.engine.impl.cmd.GetTaskFormCmd;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.impl.util.EnsureUtil;

public class GetDeployedHistoricTaskFormCmd extends AbstractGetDeployedFormCmd {

    protected String taskId;

    public GetDeployedHistoricTaskFormCmd(String taskId) {
        EnsureUtil.ensureNotNull(BadUserRequestException.class, "Task id cannot be null", "taskId", taskId);
        this.taskId = taskId;
    }

    @Override
    protected FormData getFormData() {
        return commandContext.runWithoutAuthorization(new GetHistoricTaskFormCmd(taskId));
    }

    @Override
    protected void checkAuthorization() {
    }

}
