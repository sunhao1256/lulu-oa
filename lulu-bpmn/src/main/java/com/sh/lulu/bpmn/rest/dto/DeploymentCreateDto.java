package com.sh.lulu.bpmn.rest.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DeploymentCreateDto {
    @NotBlank
    private String name;
    private String source;
    @NotNull
    private MultipartFile deploymentFile;
    private String tenantId;
    private Boolean enableDuplicateFilter = false;
    private Boolean deployChangedOnly = false;
    private String groupId;
}
