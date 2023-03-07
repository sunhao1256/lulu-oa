package com.sh.lulu.bpmn.rest.dto.repository;

import lombok.Data;
import org.camunda.bpm.engine.repository.Deployment;
import org.springframework.beans.BeanUtils;

import java.util.*;

@Data
public class DeploymentDto {

    private String id;
    private String name;
    private String source;
    private Date deploymentTime;
    private String tenantId;

    public static DeploymentDto fromDeployment(Deployment deployment){
        DeploymentDto dto = new DeploymentDto();
        BeanUtils.copyProperties(deployment,dto);
        return dto;
    }
}
