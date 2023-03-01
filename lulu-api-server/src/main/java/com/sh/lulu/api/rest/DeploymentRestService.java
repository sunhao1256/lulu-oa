package com.sh.lulu.api.rest;

import com.sh.lulu.api.rest.dto.repository.DeploymentCreateDto;
import com.sh.lulu.api.rest.dto.repository.DeploymentDto;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.repository.DeploymentWithDefinitions;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/deployment")
public class DeploymentRestService {
    private final RepositoryService repositoryService;

    @PostMapping(path = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    DeploymentDto createDeployment(
            @RequestParam(name = "deployment-file") MultipartFile multipartFile,
            @Validated DeploymentCreateDto createDto) throws IOException {
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();

        deploymentBuilder.name(createDto.getName());
        deploymentBuilder.activateProcessDefinitionsOn(createDto.getActivationTime());
        deploymentBuilder.source(createDto.getSource());
        deploymentBuilder.tenantId(createDto.getTenantId());
        deploymentBuilder.addInputStream(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
        deploymentBuilder.enableDuplicateFiltering(true);

        if (!deploymentBuilder.getResourceNames().isEmpty()) {
            DeploymentWithDefinitions deployment = deploymentBuilder.deployWithResult();
            return DeploymentDto.fromDeployment(deployment);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No deployment resources contained in the form upload.");
        }
    }
}
