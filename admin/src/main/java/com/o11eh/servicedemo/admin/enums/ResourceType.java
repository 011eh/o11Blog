package com.o11eh.servicedemo.admin.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResourceType {

    MENU("菜单"),
    OPERATION("操作");

    @JsonValue
    @EnumValue
    private final String name;
}
