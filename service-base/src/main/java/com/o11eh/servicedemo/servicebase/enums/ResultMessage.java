package com.o11eh.servicedemo.servicebase.enums;

import lombok.Getter;

@Getter
public enum ResultMessage {
    SUCCESS(200, "操作成功"),
    ERROR(500, "操作失败");

    private final Integer code;
    private final String msg;

    ResultMessage(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
