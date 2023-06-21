package com.sh.lulu.bpmn.rest.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BaseDto {

    private String id;

    private String createBy;

    private String modifyBy;

    private Date createTime;

    private Date modifyTime;
}
