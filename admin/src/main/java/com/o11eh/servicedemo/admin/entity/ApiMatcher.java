package com.o11eh.servicedemo.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.o11eh.servicedemo.admin.config.validation.RefId;
import lombok.Data;

import java.util.List;

@Data
@TableName(autoResultMap = true)
public class ApiMatcher extends BaseEntry {

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> matches;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> methods;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> noMatches;

    @RefId("back_permission")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> requiredAny;

    @RefId("back_permission")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> requiredAll;
    private boolean stopChain;
    private Integer sort;

}
