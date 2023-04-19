package com.sh.lulu.bpmn.rest;

import com.sh.lulu.auth.security.repository.UserRepository;
import com.sh.lulu.bpmn.rest.dto.StatisticsResultDto;
import com.sh.lulu.bpmn.rest.dto.repository.ProcessDefinitionStatisticsResultDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.management.ProcessDefinitionStatistics;
import org.camunda.bpm.engine.management.ProcessDefinitionStatisticsQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/process-definition")
public class ProcessDefinitionRestService {
    private final RepositoryService repositoryService;
    private final UserRepository userRepository;
    private final ManagementService mgmtService;


    @GetMapping("/statistics")
    List<StatisticsResultDto> getStatistics(@RequestParam(name = "failedJobs", required = false) Boolean includeFailedJobs,
                                            @RequestParam(name = "rootIncidents", required = false) Boolean includeRootIncidents,
                                            @RequestParam(name = "incidents", required = false) Boolean includeIncidents,
                                            @RequestParam(name = "incidentsForType", required = false) String includeIncidentsForType) {

        if (includeIncidents != null && includeIncidentsForType != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only one of the query parameter includeIncidents or includeIncidentsForType can be set.");
        }

        if (includeIncidents != null && includeRootIncidents != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only one of the query parameter includeIncidents or includeRootIncidents can be set.");
        }

        if (includeRootIncidents != null && includeIncidentsForType != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only one of the query parameter includeRootIncidents or includeIncidentsForType can be set.");
        }

        ProcessDefinitionStatisticsQuery query = mgmtService.createProcessDefinitionStatisticsQuery();

        if (includeFailedJobs != null && includeFailedJobs) {
            query.includeFailedJobs();
        }

        if (includeIncidents != null && includeIncidents) {
            query.includeIncidents();
        } else if (includeIncidentsForType != null) {
            query.includeIncidentsForType(includeIncidentsForType);
        } else if (includeRootIncidents != null && includeRootIncidents) {
            query.includeRootIncidents();
        }

        List<ProcessDefinitionStatistics> queryResults = query.unlimitedList();

        List<StatisticsResultDto> results = new ArrayList<>();
        for (ProcessDefinitionStatistics queryResult : queryResults) {
            StatisticsResultDto dto = ProcessDefinitionStatisticsResultDto.fromProcessDefinitionStatistics(queryResult);
            results.add(dto);
        }

        return results;
    }

}
