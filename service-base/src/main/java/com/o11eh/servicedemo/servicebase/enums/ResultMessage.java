package com.o11eh.servicedemo.servicebase.enums;

import com.o11eh.servicedemo.servicebase.constants.ResultCode;
import lombok.Getter;

@Getter
public enum ResultMessage {
    SUCCESS(ResultCode.SUCCESS, "操作成功"),
    ERROR(ResultCode.ERROR, "操作失败");

    private final Integer code;
    private final String msg;

    ResultMessage(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
