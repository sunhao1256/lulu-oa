package com.sh.lulu.common.enums;

import lombok.Getter;

@Getter
public enum  ResultCode implements IErrorCode{

    SUCCESS(200,"success"),

    FAILED(500,"failed"),

    VALIDATE_FAILED(400,"params validate failed"),

    UNAUTHORIZED(401, "unauthorized"),

    FORBIDDEN(403, "forbidden"),

    NOT_FOUND(404, "not found");

    private final int code;

    private final String message;

    ResultCode(int code,String message) {
        this.code = code;
        this.message=message;
    }
}
