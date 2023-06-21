package com.sh.lulu.bpmn.rest.svc;

import com.sh.lulu.bpmn.repo.CamundaProcessDefinitionGroupRelationRepository;
import com.sh.lulu.bpmn.repo.CamundaProcessDefinitionGroupRepository;
import com.sh.lulu.bpmn.repo.entity.CamundaProcessDefinitionGroup;
import com.sh.lulu.bpmn.repo.entity.CamundaProcessDefinitionGroupRelation;
import com.sh.lulu.bpmn.rest.dto.DeploymentCreateDto;
import com.sh.lulu.bpmn.rest.svc.validator.BpmnModelValidator;
import com.sh.lulu.bpmn.rest.utils.BpmnModel;
import com.sh.lulu.common.enums.ResultCode;
import com.sh.lulu.common.exception.BusinessException;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.bpmn.deployer.BpmnDeployer;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.repository.DeploymentWithDefinitions;
import org.camunda.bpm.engine.rest.dto.repository.DeploymentWithDefinitionsDto;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class DeployService {
    private final static Predicate<DeploymentCreateDto> ValidateBpmnResourceHandler = (dto -> {
        if (dto.getDeploymentFile().getOriginalFilename() == null)
            return false;
        for (String bpmnResourceSuffix : BpmnDeployer.BPMN_RESOURCE_SUFFIXES) {
            if (dto.getDeploymentFile().getOriginalFilename().endsWith(bpmnResourceSuffix)) {
                return true;
            }
        }
        return false;
    });
    @Autowired
    CamundaProcessDefinitionGroupRepository camundaProcessDefinitionGroupRepository;
    @Autowired
    CamundaProcessDefinitionGroupRelationRepository camundaProcessDefinitionGroupRelationRepository;
    @Autowired
    ProcessEngine processEngine;

    @Transactional
    public DeploymentWithDefinitionsDto doDeploy(DeploymentCreateDto dto, DeploymentBuilder deploymentBuilder) {

        //validateBpmn
        Optional<CamundaProcessDefinitionGroup> group = validateBpmnModel(dto);

        //doDeploy
        DeploymentWithDefinitions deployment = deploymentBuilder.deployWithResult();

        //persistent relation
        persistentGroupRelation(deployment, group);

        return DeploymentWithDefinitionsDto.fromDeployment(deployment);
    }

    //persistent process group relation
    private void persistentGroupRelation(DeploymentWithDefinitions deployment, Optional<CamundaProcessDefinitionGroup> group) {
        if (deployment.getDeployedProcessDefinitions() != null)
            camundaProcessDefinitionGroupRelationRepository.saveAll(
                    deployment.getDeployedProcessDefinitions()
                            .stream()
                            .map(p ->
                            {
                                CamundaProcessDefinitionGroupRelation relation = CamundaProcessDefinitionGroupRelation.builder()
                                        .processDefinitionId(p.getId())
                                        .processDefinitionName(p.getName())
                                        .processDefinitionKey(p.getKey())
                                        .deploymentId(p.getDeploymentId())
                                        .deployedTime(deployment.getDeploymentTime())
                                        .build();
                                group.ifPresent(g -> relation.setGroupId(g.getId()));
                                return relation;
                            })
                            .collect(Collectors.toList()));
    }


    @Autowired
    List<BpmnModelValidator> bpmnModelValidatorList;

    private Optional<CamundaProcessDefinitionGroup> validateBpmnModel(DeploymentCreateDto dto) {
        if (!ValidateBpmnResourceHandler.test(dto))
            return Optional.empty();

        BpmnModelInstance bpmnModelInstance;

        //read bpmn model
        try {
            bpmnModelInstance = Bpmn.readModelFromStream(dto.getDeploymentFile().getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //validator valid
        for (BpmnModelValidator validator : bpmnModelValidatorList) {
            validator.valid(bpmnModelInstance);
        }

        //validate group
        Optional<CamundaProcessDefinitionGroup> group = validateGroupExistence(bpmnModelInstance);
        return group;
    }

    private Optional<CamundaProcessDefinitionGroup> validateGroupExistence(BpmnModelInstance bpmnModelInstance) {
        //verify group existence
        Optional<String> groupId = BpmnModel.getGroupId(bpmnModelInstance);
        Optional<CamundaProcessDefinitionGroup> group = groupId.map(id -> camundaProcessDefinitionGroupRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(ResultCode.NOT_FOUND, "group not found")));
        return group;
    }
}
