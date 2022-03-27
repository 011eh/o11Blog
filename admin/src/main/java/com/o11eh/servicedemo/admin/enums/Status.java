package com.o11eh.servicedemo.admin.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {
    Enable("启用"),
    Disable("禁用");

    @JsonValue
    @EnumValue
    private final String status;

    public String getStatus() {
        return status;
    }
}
