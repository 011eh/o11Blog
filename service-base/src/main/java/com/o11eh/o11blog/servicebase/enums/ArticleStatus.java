package com.o11eh.o11blog.servicebase.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ArticleStatus {

    DRAFT(0, "草稿"),
    AUDITING(1, "审核中"),
    PUBLISHED(2, "已发布"),
    DELETE(3, "逻辑删除");

    @JsonValue
    @EnumValue
    private int value;
    private String name;

}
