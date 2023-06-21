package com.sh.lulu.common.exception;


import com.sh.lulu.common.enums.IErrorCode;
import com.sh.lulu.common.enums.ResultCode;
import org.apache.commons.lang3.ObjectUtils;

public class BusinessException extends RuntimeException {
    private final IErrorCode code;

    public BusinessException(String message) {
        super(message);
        this.code = ResultCode.FAILED;
    }

    public BusinessException(IErrorCode iErrorCode, String message) {
        super(message);
        this.code = iErrorCode;
    }

    public IErrorCode getCode() {
        return code;
    }

    public static void Assert(Object o, String msg) {
        if (ObjectUtils.isEmpty(o)) {
            throw new BusinessException(msg);
        }
    }
}

