package com.sh.lulu.api.rest.dto.repository;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class DeploymentCreateDto {
    @NotBlank
    private String name;
    private Date activationTime;
    private String source;
    private String tenantId;
}
