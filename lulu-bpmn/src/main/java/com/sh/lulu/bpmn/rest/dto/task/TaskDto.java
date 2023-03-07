/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sh.lulu.bpmn.rest.dto.task;

import lombok.Data;
import org.camunda.bpm.engine.BadUserRequestException;
import org.camunda.bpm.engine.form.CamundaFormRef;
import org.camunda.bpm.engine.task.Task;

import java.util.Date;

@Data
public class TaskDto {

    private String id;
    private String name;
    private String assignee;
    private Date created;
    private Date due;
    private Date followUp;
    private Date lastUpdated;
    private String delegationState;
    private String description;
    private String executionId;
    private String owner;
    private String parentTaskId;
    private int priority;
    private String processDefinitionId;
    private String processInstanceId;
    private String taskDefinitionKey;
    private String caseExecutionId;
    private String caseInstanceId;
    private String caseDefinitionId;
    private boolean suspended;
    private String formKey;
    private CamundaFormRef camundaFormRef;
    private String tenantId;

    public static TaskDto fromEntity(Task task) {
        TaskDto dto = new TaskDto();
        dto.id = task.getId();
        dto.name = task.getName();
        dto.assignee = task.getAssignee();
        dto.created = task.getCreateTime();
        dto.lastUpdated = task.getLastUpdated();
        dto.due = task.getDueDate();
        dto.followUp = task.getFollowUpDate();

        if (task.getDelegationState() != null) {
            dto.delegationState = task.getDelegationState().toString();
        }

        dto.description = task.getDescription();
        dto.executionId = task.getExecutionId();
        dto.owner = task.getOwner();
        dto.parentTaskId = task.getParentTaskId();
        dto.priority = task.getPriority();
        dto.processDefinitionId = task.getProcessDefinitionId();
        dto.processInstanceId = task.getProcessInstanceId();
        dto.taskDefinitionKey = task.getTaskDefinitionKey();
        dto.caseDefinitionId = task.getCaseDefinitionId();
        dto.caseExecutionId = task.getCaseExecutionId();
        dto.caseInstanceId = task.getCaseInstanceId();
        dto.suspended = task.isSuspended();
        dto.tenantId = task.getTenantId();

        try {
            dto.formKey = task.getFormKey();
            dto.camundaFormRef = task.getCamundaFormRef();
        } catch (BadUserRequestException e) {
            // ignore (initializeFormKeys was not called)
        }
        return dto;
    }


}
