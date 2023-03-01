package com.sh.lulu.api.rest.dto.runtime;

import org.camunda.bpm.engine.runtime.ProcessInstance;

public class ProcessInstanceDto {

    private String id;
    private String definitionId;
    private String businessKey;
    private String caseInstanceId;
    private boolean ended;
    private boolean suspended;
    private String tenantId;

    public ProcessInstanceDto() {
    }

    public ProcessInstanceDto(ProcessInstance instance) {
        this.id = instance.getId();
        this.definitionId = instance.getProcessDefinitionId();
        this.businessKey = instance.getBusinessKey();
        this.caseInstanceId = instance.getCaseInstanceId();
        this.ended = instance.isEnded();
        this.suspended = instance.isSuspended();
        this.tenantId = instance.getTenantId();
    }

    public String getId() {
        return id;
    }

    public String getDefinitionId() {
        return definitionId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public String getCaseInstanceId() {
        return caseInstanceId;
    }

    public boolean isEnded() {
        return ended;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public String getTenantId() {
        return tenantId;
    }

    public static ProcessInstanceDto fromProcessInstance(ProcessInstance instance) {
        return new ProcessInstanceDto(instance);
    }

}
