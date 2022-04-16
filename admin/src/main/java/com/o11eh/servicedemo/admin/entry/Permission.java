package com.o11eh.servicedemo.admin.entry;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.fasterxml.jackson.databind.deser.std.ThrowableDeserializer;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.o11eh.servicedemo.admin.enums.ResourceType;
import com.o11eh.servicedemo.admin.enums.Status;
import com.o11eh.servicedemo.validation.ColumnsUnique;
import com.o11eh.servicedemo.validation.RefId;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    @JsonDeserialize(using = ThrowableDeserializer.class)
    private RouterInfo routerInfo;
}
