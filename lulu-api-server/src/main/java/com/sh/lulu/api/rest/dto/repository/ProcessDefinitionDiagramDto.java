package com.sh.lulu.api.rest.dto.repository;

import lombok.Data;

@Data
public class ProcessDefinitionDiagramDto {

    private String id;
    private String bpmn20Xml;

    public static ProcessDefinitionDiagramDto create(String id, String bpmn20Xml) {
        ProcessDefinitionDiagramDto processDefinitionDiagramDto = new ProcessDefinitionDiagramDto();
        processDefinitionDiagramDto.id = id;
        processDefinitionDiagramDto.bpmn20Xml = bpmn20Xml;
        return processDefinitionDiagramDto;
    }

}
