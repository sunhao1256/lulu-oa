package com.sh.lulu.api.rest.dto.repository;

import lombok.Data;
import org.camunda.bpm.engine.repository.Resource;
import org.springframework.beans.BeanUtils;

@Data
public class DeploymentResourceDto {
    protected String id;
    protected String name;
    protected String deploymentId;

    public static DeploymentResourceDto fromResource(Resource resource) {
        DeploymentResourceDto dto = new DeploymentResourceDto();
        BeanUtils.copyProperties(resource, dto);
        return dto;
    }

}
