package com.sh.lulu.bpmn.rest.svc.validator;

import org.camunda.bpm.model.bpmn.BpmnModelInstance;

public interface BpmnModelValidator {
    void valid(BpmnModelInstance bpmnModelInstance) throws BpmnModelNotValidException;
}
