package com.sh.lulu.api.controller;

import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import org.camunda.community.rest.client.api.DeploymentApi;
import org.camunda.community.rest.client.dto.DeploymentDto;
import org.camunda.community.rest.client.dto.DeploymentResourceDto;
import org.camunda.community.rest.client.invoker.ApiException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class Processor {

    private final DeploymentApi deploymentApi;

    @PostMapping("/processor/create")
    public Map create() {
        return null;
    }
}
