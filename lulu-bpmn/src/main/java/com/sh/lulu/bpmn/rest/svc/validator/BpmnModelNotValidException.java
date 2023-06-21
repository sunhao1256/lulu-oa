package com.sh.lulu.bpmn.rest.svc.validator;

import com.sh.lulu.common.enums.ResultCode;
import com.sh.lulu.common.exception.BusinessException;

public class BpmnModelNotValidException extends BusinessException {
    public BpmnModelNotValidException(String message) {
        super(ResultCode.VALIDATE_FAILED, message);
    }
}
