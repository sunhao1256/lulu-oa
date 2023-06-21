package com.sh.lulu.bpmn.rest;

import com.sh.lulu.bpmn.rest.dto.DeploymentCreateDto;
import com.sh.lulu.bpmn.rest.svc.DeployService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.repository.DeploymentWithDefinitions;
import org.camunda.bpm.engine.rest.dto.repository.DeploymentWithDefinitionsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/deployment")
public class Deployment {
    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DeploymentWithDefinitionsDto create(@ModelAttribute DeploymentCreateDto dto) {
        return createDeployment(dto);
    }

    @Autowired
    DeployService deployService;

    public DeploymentWithDefinitionsDto createDeployment(DeploymentCreateDto dto) {
        DeploymentBuilder deploymentBuilder = null;
        try {
            deploymentBuilder = extractDeploymentInformation(dto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!deploymentBuilder.getResourceNames().isEmpty()) {
            return deployService.doDeploy(dto, deploymentBuilder);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No deployment resources contained in the form upload.");
        }
    }


    @Autowired
    ProcessEngine processEngine;

    private DeploymentBuilder extractDeploymentInformation(DeploymentCreateDto dto) throws IOException {
        DeploymentBuilder deploymentBuilder = processEngine.getRepositoryService().createDeployment();

        deploymentBuilder.addInputStream(dto.getDeploymentFile().getOriginalFilename(), new ByteArrayInputStream(dto.getDeploymentFile().getBytes()));

        if (dto.getName() != null) {
            deploymentBuilder.name(dto.getName());
        }

        if (dto.getSource() != null) {
            deploymentBuilder.source(dto.getSource());
        }

        if (dto.getTenantId() != null) {
            deploymentBuilder.tenantId(dto.getTenantId());
        }
        extractDuplicateFilteringForDeployment(dto, deploymentBuilder);
        return deploymentBuilder;

    }

    private void extractDuplicateFilteringForDeployment(DeploymentCreateDto dto, DeploymentBuilder deploymentBuilder) {
        Boolean enableDuplicateFiltering = dto.getEnableDuplicateFilter();
        Boolean deployChangedOnly = dto.getDeployChangedOnly();
        // deployChangedOnly overrides the enableDuplicateFiltering setting
        if (deployChangedOnly) {
            deploymentBuilder.enableDuplicateFiltering(true);
        } else if (enableDuplicateFiltering) {
            deploymentBuilder.enableDuplicateFiltering(false);
        }
    }
}
