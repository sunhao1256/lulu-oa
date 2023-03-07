package com.sh.lulu.auth.model;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Base {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "CREATE_BY")
    @CreatedBy
    @NotNull
    private String createBy;


    @Column(name = "MODIFY_BY")
    @LastModifiedBy
    @NotNull
    private String modifyBy;

    @Column(name = "CREATE_TIME")
    @CreatedDate
    @NotNull
    private Date createTime;


    @Column(name = "MODIFY_TIME")
    @LastModifiedDate
    @NotNull
    private Date modifyTime;
}
