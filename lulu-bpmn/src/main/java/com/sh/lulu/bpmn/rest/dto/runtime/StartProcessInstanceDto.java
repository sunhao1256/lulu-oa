package com.sh.lulu.bpmn.rest.dto.runtime;

import com.sh.lulu.bpmn.rest.dto.VariableValueDto;
import lombok.Data;

import java.util.Map;

@Data
public class StartProcessInstanceDto {
    protected Map<String, VariableValueDto> variables;
    protected String businessKey;
    protected String caseInstanceId;
    protected boolean skipCustomListeners;
    protected boolean skipIoMappings;
    protected boolean withVariablesInReturn = false;
}
