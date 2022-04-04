package com.o11eh.servicedemo.admin.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.o11eh.servicedemo.admin.enums.ResourceType;
import com.o11eh.servicedemo.admin.enums.Status;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 011eh
 * @since 2022/02/27 12:18
 */
@Data
@TableName(value = "back_permission", autoResultMap = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Permission extends BaseEntry<Permission> {
    private String name;
    private String permissionKey;
    private ResourceType resourceType;
    private String parentId;
    private Integer sort;
    private Status status;

    @TableField(exist = false)
    private List<Permission> children;

    @JsonUnwrapped
    @TableField(typeHandler = JacksonTypeHandler.class)
    private RouterInfo routerInfo;
}
