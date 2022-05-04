package com.o11eh.servicedemo.admin.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResourceType {

    MENU("一级菜单", 0),
    SUB_MENU("二级菜单", 1),
    OPERATION("操作", 2);

    @JsonValue
    @EnumValue
    private final String type;

    private final int sort;

}
