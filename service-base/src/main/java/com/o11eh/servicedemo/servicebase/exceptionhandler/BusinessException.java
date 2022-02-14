package com.o11eh.servicedemo.servicebase.exceptionhandler;

import com.o11eh.servicedemo.servicebase.enums.ResultMessage;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends RuntimeException {

    private Integer code;
    private String msg;


    public static BusinessException e(ResultMessage resultMessage) {
        return new BusinessException(resultMessage.getCode(), resultMessage.getMsg());
    }
}
