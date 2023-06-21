package com.sh.lulu.bpmn.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CamundaProcessDefinitionGroupCreateDto {
    @NotBlank
    private String name;
}
