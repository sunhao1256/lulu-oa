package com.sh.lulu.bpmn.rest.dto.runtime;

import lombok.Getter;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.camunda.bpm.engine.variable.VariableMap;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ProcessInstanceWithVariablesDto extends ProcessInstanceDto {

    private Map<String, Object> variables;

    public ProcessInstanceWithVariablesDto() {
    }

    public ProcessInstanceWithVariablesDto(ProcessInstance instance) {
        super(instance);
    }


    public static ProcessInstanceDto fromProcessInstance(ProcessInstanceWithVariables instance) {
        ProcessInstanceWithVariablesDto result = new ProcessInstanceWithVariablesDto(instance);
        VariableMap variables = instance.getVariables();

        Map<String, Object> resultVariables = new HashMap<>();
        for (String variableName : variables.keySet()) {
            resultVariables.put(variableName, variables.get(variableName));
        }

        result.variables = resultVariables;
        return result;
    }
}
