package com.sh.lulu.bpmn.repo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACT_RE_CAMFORMDEF")
@Data
public class CamundaFormDefinitionEntity {
    @Id
    @Column(name = "ID_")
    protected String id;
    @Column(name = "REV_")
    protected int revision = 1;
    @Column(name = "KEY_")
    protected String key;
    @Column(name = "VERSION_")
    protected int version;
    @Column(name = "DEPLOYMENT_ID_")
    protected String deploymentId;
    @Column(name = "RESOURCE_NAME_")
    protected String resourceName;
    @Column(name = "TENANT_ID_")
    protected String tenantId;

}
