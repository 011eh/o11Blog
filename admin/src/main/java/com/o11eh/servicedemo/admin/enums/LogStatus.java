package com.o11eh.servicedemo.admin.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LogStatus {

    SUCCESS(0, "成功"),
    FAIL(1, "失败");

    @EnumValue
    private final int DBValue;

    @JsonValue
    private final String logStatus;

}