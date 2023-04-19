package com.sh.lulu.bpmn.rest.dto.repository;

import org.camunda.bpm.engine.management.IncidentStatistics;

public class IncidentStatisticsResultDto {

    protected String incidentType;
    protected Integer incidentCount;

    public IncidentStatisticsResultDto() {
    }

    public String getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public Integer getIncidentCount() {
        return incidentCount;
    }

    public void setIncidentCount(Integer incidentCount) {
        this.incidentCount = incidentCount;
    }

    public static IncidentStatisticsResultDto fromIncidentStatistics(IncidentStatistics statistics) {
        IncidentStatisticsResultDto dto = new IncidentStatisticsResultDto();
        dto.setIncidentType(statistics.getIncidentType());
        dto.setIncidentCount(statistics.getIncidentCount());
        return dto;
    }

}
