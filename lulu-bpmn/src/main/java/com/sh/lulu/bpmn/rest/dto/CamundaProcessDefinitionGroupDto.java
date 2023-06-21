package com.sh.lulu.bpmn.rest.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class CamundaProcessDefinitionGroupDto extends BaseDto {
    private String name;
}
