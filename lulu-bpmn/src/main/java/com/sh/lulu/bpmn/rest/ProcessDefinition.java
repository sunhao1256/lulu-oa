package com.sh.lulu.bpmn.rest;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.lulu.bpmn.repo.CamundaProcessDefinitionGroupRelationRepository;
import com.sh.lulu.bpmn.repo.CamundaProcessDefinitionGroupRepository;
import com.sh.lulu.bpmn.repo.entity.CamundaProcessDefinitionGroup;
import com.sh.lulu.bpmn.repo.entity.CamundaProcessDefinitionGroupRelation;
import com.sh.lulu.bpmn.repo.entity.QCamundaProcessDefinitionGroup;
import com.sh.lulu.bpmn.rest.dto.CamundaProcessDefinitionGroupCreateDto;
import com.sh.lulu.common.enums.ResultCode;
import com.sh.lulu.common.response.CommonResult;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/process-definition")
@Validated
public class ProcessDefinition {
    @Autowired
    FormService formService;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    JPAQueryFactory jpaQueryFactory;
    @Autowired
    CamundaProcessDefinitionGroupRepository camundaProcessDefinitionGroupRepository;
    @Autowired
    CamundaProcessDefinitionGroupRelationRepository camundaProcessDefinitionGroupRelationRepository;

    @GetMapping("/group/{id}")
    public CommonResult<?> getGroup(@NotBlank @PathVariable("id") String id) {
        return camundaProcessDefinitionGroupRepository.findById(id)
                .map(CommonResult::success)
                .orElseGet(() -> CommonResult.failed(ResultCode.NOT_FOUND, "group not found"));
    }

    @GetMapping("/group")
    public List<CamundaProcessDefinitionGroup> groups() {
        return camundaProcessDefinitionGroupRepository.findAll();
    }


    @PostMapping(path = "/group")
    public CommonResult<?> createGroup(@Validated @RequestBody CamundaProcessDefinitionGroupCreateDto dto) {
        return camundaProcessDefinitionGroupRepository
                .findOne(QCamundaProcessDefinitionGroup.camundaProcessDefinitionGroup.name.eq(dto.getName()))
                .map(c -> CommonResult.failed(ResultCode.VALIDATE_FAILED, "duplicated group name"))
                .orElseGet(() -> CommonResult.success(
                                camundaProcessDefinitionGroupRepository
                                        .save(CamundaProcessDefinitionGroup.builder().name(dto.getName()).build())
                        )
                );
    }

    @DeleteMapping("/group/{id}")
    public void deleteGroup(@NotBlank @PathVariable String id) {
        camundaProcessDefinitionGroupRepository
                .delete(CamundaProcessDefinitionGroup.builder().id(id).build());
    }


    @GetMapping(path = {"/group/{id}/process-definition", "/group/process-definition"})
    public CommonResult<?> getProcessDefinition(@PathVariable(required = false) String id) {
        if (ObjectUtils.isEmpty(id)) {
            return CommonResult.success(camundaProcessDefinitionGroupRelationRepository
                    .findAll());
        }
        return camundaProcessDefinitionGroupRepository
                .findById(id)
                .map(g -> CommonResult.success(g.getRelations()))
                .orElseGet(() -> CommonResult.failed(ResultCode.NOT_FOUND, "group not found"));
    }
}
