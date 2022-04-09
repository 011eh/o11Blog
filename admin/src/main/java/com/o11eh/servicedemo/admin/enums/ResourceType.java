package com.o11eh.servicedemo.admin.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResourceType {

    MENU(0, "一级菜单"),
    SUB_MENU(1, "二级菜单"),
    OPERATION(2, "操作");

    private int sort;

    @JsonValue
    @EnumValue
    private final String type;


}
