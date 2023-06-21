package com.sh.lulu.bpmn.rest.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CamundaFormDefinitionQuery extends BaseQuery {
    private String Id;
    private String key;
}
