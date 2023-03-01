package com.sh.lulu.common.response;

import com.sh.lulu.common.enums.IErrorCode;
import com.sh.lulu.common.enums.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonResult<T> {

    private long code;

    private String message;

    private T data;

    public static CommonResult success() {
        return new CommonResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }
    public static <T> CommonResult success(T data, String message) {
        return new CommonResult(ResultCode.SUCCESS.getCode(), message, data);
    }

    public static <T> CommonResult success(T data) {
        return new CommonResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), message, null);
    }
}
