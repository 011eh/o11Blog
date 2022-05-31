package com.o11eh.servicedemo.admin.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.util.List;

@Data
@TableName(autoResultMap = true)
public class ApiMatcher extends BaseEntry{

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> matches;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> methods;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> noMatches;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> requiredAny;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> requiredAll;
    private boolean stopChain;
    private Integer sort;

}
