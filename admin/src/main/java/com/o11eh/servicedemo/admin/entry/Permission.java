package com.o11eh.servicedemo.admin.entry;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.o11eh.servicedemo.admin.config.validation.ColumnsUnique;
import com.o11eh.servicedemo.admin.config.validation.RefId;
import com.o11eh.servicedemo.admin.enums.ResourceType;
import com.o11eh.servicedemo.admin.enums.Status;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 011eh
 * @since 2022/02/27 12:18
 */
@Data
@TableName(value = "back_permission", autoResultMap = true)
@ColumnsUnique(tableName = "back_permission", properties = "name")
public class Permission extends BaseEntry {

    @NotEmpty
    private String name;

    @NotEmpty
    private String permissionKey;

    @NotNull
    private ResourceType resourceType;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @RefId("back_permission")
    private String parentId;
    private Integer sort;
    private Status status;

    @TableField(exist = false)
    private List<Permission> children;

    @JsonUnwrapped
    @TableField(typeHandler = JacksonTypeHandler.class, updateStrategy = FieldStrategy.IGNORED)
    private RouterInfo routerInfo;
}
