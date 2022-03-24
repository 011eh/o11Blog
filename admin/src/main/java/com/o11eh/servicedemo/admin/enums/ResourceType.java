package com.o11eh.servicedemo.admin.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResourceType {

    MENU(1, "菜单"),

    OPERATION(2, "操作");

    private int value;
    @EnumValue
    @JsonValue
    private String name;
}
