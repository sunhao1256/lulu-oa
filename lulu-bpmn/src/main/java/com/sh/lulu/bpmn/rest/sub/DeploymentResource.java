package com.sh.lulu.bpmn.rest.sub;


import com.sh.lulu.bpmn.rest.dto.repository.DeploymentDto;
import com.sh.lulu.bpmn.rest.dto.repository.DeploymentResourceDto;
import com.sh.lulu.common.response.CommonResult;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/deployment/{id}")
public class DeploymentResource {
    private final RepositoryService repositoryService;

    @GetMapping
    DeploymentDto getDeployment(
            @PathVariable("id") String deploymentId) {
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
        if (deployment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Deployment with id '" + deploymentId + "' does not exist");
        }
        return DeploymentDto.fromDeployment(deployment);
    }

    @GetMapping("/{resourceId}")
    DeploymentResourceDto getDeploymentResource(
            @PathVariable("id") String deploymentId,
            @PathVariable("resourceId") String resourceId) {
        List<DeploymentResourceDto> deploymentResources = getDeploymentResources(deploymentId);
        for (DeploymentResourceDto deploymentResource : deploymentResources) {
            if (deploymentResource.getId().equals(resourceId)) {
                return deploymentResource;
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Deployment resource with resource id '" + resourceId + "' for deployment id '" + deploymentId + "' does not exist.");
    }

    @GetMapping("/resources")
    List<DeploymentResourceDto> getDeploymentResources(
            @PathVariable("id") String deploymentId
    ) {

        List<Resource> resources = repositoryService.getDeploymentResources(deploymentId);

        List<DeploymentResourceDto> deploymentResources = new ArrayList<>();
        for (Resource resource : resources) {
            deploymentResources.add(DeploymentResourceDto.fromResource(resource));
        }

        if (!deploymentResources.isEmpty()) {
            return deploymentResources;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Deployment resources for deployment id '" + deploymentId + "' do not exist.");
        }
    }

    @DeleteMapping
    CommonResult deleteDeployment(@PathVariable("id") String deploymentId,
                                  @RequestParam(value = "cascade", required = false) boolean cascade,
                                  @RequestParam(value = "skipCustomListeners", required = false) boolean skipCustomListeners,
                                  @RequestParam(value = "skipIoMappings", required = false) boolean skipIoMappings) {
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
        if (deployment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Deployment with id '" + deploymentId + "' do not exist");
        }
        repositoryService.deleteDeployment(deploymentId, cascade, skipCustomListeners, skipIoMappings);
        return CommonResult.success();
    }


}
