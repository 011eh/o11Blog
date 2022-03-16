package com.o11eh.servicedemo.base.exception;

import com.o11eh.servicedemo.base.constants.ResultCode;
import com.o11eh.servicedemo.base.constants.ResultMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends RuntimeException {

    private Integer code;

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public static BusinessException e(String message) {
        return new BusinessException(ResultCode.ERROR, message);
    }
}
