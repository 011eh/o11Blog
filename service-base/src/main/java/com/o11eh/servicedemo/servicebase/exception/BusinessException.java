package com.o11eh.servicedemo.servicebase.exception;

import com.o11eh.servicedemo.servicebase.constants.ResultCode;
import com.o11eh.servicedemo.servicebase.enums.ResultMessage;
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

    public static BusinessException e(ResultMessage resultMessage) {
        return new BusinessException(resultMessage.getCode(), resultMessage.getMsg());
    }

    public static BusinessException e(String message) {
        return new BusinessException(ResultCode.ERROR, message);
    }
}
