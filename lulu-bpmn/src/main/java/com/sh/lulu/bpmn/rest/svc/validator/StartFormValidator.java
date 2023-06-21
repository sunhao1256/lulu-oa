package com.sh.lulu.bpmn.rest.svc.validator;

import com.sh.lulu.common.enums.ResultCode;
import com.sh.lulu.common.exception.BusinessException;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collection;

@Component
public class StartFormValidator implements BpmnModelValidator {
    @Override
    public void valid(BpmnModelInstance instance) throws BpmnModelNotValidException {
        Collection<StartEvent> startEvent = instance.getModelElementsByType(StartEvent.class);
        if (ObjectUtils.isEmpty(startEvent) || startEvent.size() > 1) {
            throw new BpmnModelNotValidException("Process is Mandatory to Comprise Only One StartEvent");
        }
    }
}
