package com.sh.lulu.bpmn.repo;


import com.sh.lulu.bpmn.repo.entity.CamundaProcessDefinitionGroup;
import com.sh.lulu.bpmn.repo.entity.CamundaProcessDefinitionGroupRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CamundaProcessDefinitionGroupRelationRepository extends JpaRepository<CamundaProcessDefinitionGroupRelation, String>,
        QuerydslPredicateExecutor<CamundaProcessDefinitionGroupRelation> {
}
