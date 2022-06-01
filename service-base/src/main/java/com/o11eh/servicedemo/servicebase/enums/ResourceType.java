package com.o11eh.servicedemo.servicebase.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResourceType {

    MENU(0, "一级菜单", 0),
    SUB_MENU(1, "二级菜单", 1),
    OPERATION(2, "操作", 2);

    @EnumValue
    private final int DBValue;

    @JsonValue
    private final String type;
    private final int sort;

}
