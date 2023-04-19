package com.sh.lulu.bpmn.rest.dto;

import com.sh.lulu.bpmn.rest.dto.repository.IncidentStatisticsResultDto;

import java.util.List;

public abstract class StatisticsResultDto {

    protected String id;
    protected Integer instances;
    protected Integer failedJobs;
    protected List<IncidentStatisticsResultDto> incidents;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getInstances() {
        return instances;
    }

    public void setInstances(Integer instances) {
        this.instances = instances;
    }

    public Integer getFailedJobs() {
        return failedJobs;
    }

    public void setFailedJobs(Integer failedJobs) {
        this.failedJobs = failedJobs;
    }

    public List<IncidentStatisticsResultDto> getIncidents() {
        return incidents;
    }

    public void setIncidents(List<IncidentStatisticsResultDto> incidents) {
        this.incidents = incidents;
    }

}
