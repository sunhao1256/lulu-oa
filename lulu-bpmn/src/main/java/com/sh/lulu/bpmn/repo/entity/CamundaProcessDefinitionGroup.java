package com.sh.lulu.bpmn.repo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sh.lulu.common.model.Base;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "CAM_PROCDEF_GROUP")
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class CamundaProcessDefinitionGroup extends Base {
    @Column(name = "name", unique = true, nullable = false)
    private String name;


    @OneToMany
    @JoinColumn(name = "group_id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private Set<CamundaProcessDefinitionGroupRelation> relations;
}
