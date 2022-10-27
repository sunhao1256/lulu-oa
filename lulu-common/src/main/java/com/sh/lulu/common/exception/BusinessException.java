package com.sh.lulu.common.exception;


import org.apache.commons.lang3.ObjectUtils;

public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }
    public static void Assert(Object o,String msg){
        if(ObjectUtils.isEmpty(o)){
            throw new BusinessException(msg);
        }
    }
}

