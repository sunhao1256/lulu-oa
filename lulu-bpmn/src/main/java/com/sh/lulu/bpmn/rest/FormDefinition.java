package com.sh.lulu.bpmn.rest;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.lulu.bpmn.repo.cmd.GetDeployedHistoricTaskFormCmd;
import com.sh.lulu.bpmn.repo.cmd.GetHistoricTaskFormCmd;
import com.sh.lulu.bpmn.repo.entity.CamundaFormDefinitionEntity;
import com.sh.lulu.bpmn.repo.entity.QCamundaFormDefinitionEntity;
import com.sh.lulu.bpmn.rest.dto.CamundaFormDefinitionDto;
import com.sh.lulu.bpmn.rest.dto.CamundaFormDefinitionQuery;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/form-definition")
public class FormDefinition {
    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    FormService formService;

    @Autowired
    HistoryService historyService;

    @GetMapping("")
    public List<CamundaFormDefinitionDto> ListDefinition(CamundaFormDefinitionQuery query) {
        JPAQuery<CamundaFormDefinitionEntity> execute = jpaQueryFactory.selectFrom(QCamundaFormDefinitionEntity.camundaFormDefinitionEntity);
        Optional.ofNullable(query.getId()).ifPresent(id -> execute.where(QCamundaFormDefinitionEntity.camundaFormDefinitionEntity.id.eq(id)));
        Optional.ofNullable(query.getKey()).ifPresent(key -> execute.where(QCamundaFormDefinitionEntity.camundaFormDefinitionEntity.key.eq(key)));
        Optional.ofNullable(query.getSortBy()).ifPresent(sortBy -> {
            execute.orderBy(getSortedColumn(query.getSortOrder() != null && query.getSortOrder().equals("asc") ? Order.ASC : Order.DESC, sortBy));
        });
        return execute.fetch().stream().map(CamundaFormDefinitionDto::fromCamundaFormDefinition).collect(Collectors.toList());
    }

    private OrderSpecifier<?> getSortedColumn(Order order, String fieldName) {
        Path<Object> fieldPath = Expressions.path(Object.class, QCamundaFormDefinitionEntity.camundaFormDefinitionEntity, fieldName);
        return new OrderSpecifier(order, fieldPath);
    }


}
