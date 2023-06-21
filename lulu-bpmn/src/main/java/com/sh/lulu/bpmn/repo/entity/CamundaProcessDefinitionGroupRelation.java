package com.sh.lulu.bpmn.repo.entity;

import com.sh.lulu.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "CAM_PROCDEF_GROUP_REL")
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class CamundaProcessDefinitionGroupRelation extends Base {

    @Column(name = "group_id", nullable = true)
    private String groupId;
    @Column(name = "procdef_id", nullable = false)
    private String processDefinitionId;
    @Column(name = "procdef_key", nullable = false)
    private String processDefinitionKey;
    @Column(name = "procdef_name", nullable = false)
    private String processDefinitionName;
    @Column(name = "deployed_time", nullable = false)
    private Date deployedTime;
    @Column(name = "deployment_id", nullable = false)
    private String deploymentId;
}
