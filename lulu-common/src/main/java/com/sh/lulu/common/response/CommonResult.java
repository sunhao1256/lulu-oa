package com.sh.lulu.common.response;

import com.sh.lulu.common.enums.IErrorCode;
import com.sh.lulu.common.enums.ResultCode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonResult<T> {

    private long code;

    private String message;

    private T data;
}
