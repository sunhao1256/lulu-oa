package com.sh.lulu.bpmn.rest.utils;

import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.impl.instance.ProcessImpl;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperties;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperty;
import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.Optional;

public class BpmnModel {
    private static final String GROUP_ID = "groupId";

    public static Optional<String> getGroupId(BpmnModelInstance bpmnModelInstance) {
        return bpmnModelInstance.getModelElementsByType(Process.class)
                .stream()
                .map(p -> {
                    ExtensionElements extensionElements = p.getExtensionElements();
                    if (extensionElements == null)
                        return null;
                    CamundaProperties camundaProperties = extensionElements.getElementsQuery()
                            .filterByType(CamundaProperties.class)
                            .singleResult();
                    return getPropertyValue(camundaProperties, GROUP_ID);
                })
                .filter(g -> !ObjectUtils.isEmpty(g))
                .findFirst();
    }

    public static String getPropertyValue(CamundaProperties properties, String key) {
        for (CamundaProperty camundaProperty : properties.getCamundaProperties()) {
            if (camundaProperty.getCamundaName().equals(key)) {
                return camundaProperty.getCamundaValue();
            }
        }
        return null;
    }
}
