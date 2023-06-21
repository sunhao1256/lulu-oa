package com.sh.lulu.bpmn.repo;


import com.sh.lulu.bpmn.repo.entity.CamundaProcessDefinitionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CamundaProcessDefinitionGroupRepository extends JpaRepository<CamundaProcessDefinitionGroup, String>,
        QuerydslPredicateExecutor<CamundaProcessDefinitionGroup> {
}
