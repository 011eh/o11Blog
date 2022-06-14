package com.o11eh.servicedemo.servicebase.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {

    Disable(0, "禁用"),
    Enable(1, "启用"),
    FROZEN(2, "冻结");

    @EnumValue
    private int DBValue;

    @JsonValue
    private final String status;

}
