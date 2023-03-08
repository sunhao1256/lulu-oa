package com.sh.lulu.common.model;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
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

    @Column(name = "CREATE_BY", nullable = false)
    @CreatedBy
    private String createBy;


    @Column(name = "MODIFY_BY", nullable = false)
    @LastModifiedBy
    private String modifyBy;

    @Column(name = "CREATE_TIME", nullable = false)
    @CreatedDate
    private Date createTime;


    @Column(name = "MODIFY_TIME", nullable = false)
    @LastModifiedDate
    private Date modifyTime;
}
