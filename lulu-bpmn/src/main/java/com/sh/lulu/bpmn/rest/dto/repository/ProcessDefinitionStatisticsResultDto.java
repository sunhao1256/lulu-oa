package com.sh.lulu.bpmn.rest.dto.repository;

import com.sh.lulu.bpmn.rest.dto.StatisticsResultDto;
import org.camunda.bpm.engine.management.IncidentStatistics;
import org.camunda.bpm.engine.management.ProcessDefinitionStatistics;

import java.util.ArrayList;

public class ProcessDefinitionStatisticsResultDto extends StatisticsResultDto {

    private ProcessDefinitionDto definition;

    public ProcessDefinitionDto getDefinition() {
        return definition;
    }

    public void setDefinition(ProcessDefinitionDto definition) {
        this.definition = definition;
    }

    public static ProcessDefinitionStatisticsResultDto fromProcessDefinitionStatistics(ProcessDefinitionStatistics statistics) {
        ProcessDefinitionStatisticsResultDto dto = new ProcessDefinitionStatisticsResultDto();

        dto.definition = ProcessDefinitionDto.fromProcessDefinition(statistics);
        dto.id = statistics.getId();
        dto.instances = statistics.getInstances();
        dto.failedJobs = statistics.getFailedJobs();

        dto.incidents = new ArrayList<IncidentStatisticsResultDto>();
        for (IncidentStatistics incident : statistics.getIncidentStatistics()) {
            IncidentStatisticsResultDto incidentDto = IncidentStatisticsResultDto.fromIncidentStatistics(incident);
            dto.incidents.add(incidentDto);
        }

        return dto;
    }
}
