package com.sh.lulu.bpmn.rest.svc.validator;

import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collection;

@Component
public class ProcessValidator implements BpmnModelValidator {
    @Override
    public void valid(BpmnModelInstance instance) throws BpmnModelNotValidException {
        Collection<Process> processes = instance.getModelElementsByType(Process.class);
        if (ObjectUtils.isEmpty(processes))
            throw new BpmnModelNotValidException("Require At Least One Process");

        for (Process process : processes) {
            String name = process.getName();
            if (ObjectUtils.isEmpty(name)) {
                throw new BpmnModelNotValidException("Process Name is Mandatory");
            }
        }

    }
}
