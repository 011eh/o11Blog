package com.o11eh.servicedemo.servicebase.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LogStatus {

    SUCCESS(0, "正常"),
    FAIL(1, "异常");

    @EnumValue
    private final int DBValue;

    @JsonValue
    private final String logStatus;

}
