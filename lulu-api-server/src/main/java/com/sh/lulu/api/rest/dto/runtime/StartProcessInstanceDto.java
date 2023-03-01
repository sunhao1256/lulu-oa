package com.sh.lulu.api.rest.dto.runtime;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class StartProcessInstanceDto {
    protected Map<String,Object> variables;
    protected String businessKey;
    protected String caseInstanceId;
    protected boolean skipCustomListeners;
    protected boolean skipIoMappings;
    protected boolean withVariablesInReturn = false;
}
