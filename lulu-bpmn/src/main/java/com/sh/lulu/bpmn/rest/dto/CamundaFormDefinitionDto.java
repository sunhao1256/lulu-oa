package com.sh.lulu.bpmn.rest.dto;

import com.sh.lulu.bpmn.repo.entity.CamundaFormDefinitionEntity;
import lombok.Data;

@Data
public class CamundaFormDefinitionDto {
    protected String id;
    protected int revision = 1;
    protected String key;
    protected int version;
    protected String deploymentId;
    protected String resourceName;
    protected String tenantId;


    public static CamundaFormDefinitionDto fromCamundaFormDefinition(CamundaFormDefinitionEntity camundaFormDefinition) {
        CamundaFormDefinitionDto dto = new CamundaFormDefinitionDto();
        dto.setId(camundaFormDefinition.getId());
        dto.setKey(camundaFormDefinition.getKey());
        dto.setVersion(camundaFormDefinition.getVersion());
        dto.setDeploymentId(camundaFormDefinition.getDeploymentId());
        dto.setResourceName(camundaFormDefinition.getResourceName());
        dto.setTenantId(camundaFormDefinition.getTenantId());
        return dto;
    }

}
