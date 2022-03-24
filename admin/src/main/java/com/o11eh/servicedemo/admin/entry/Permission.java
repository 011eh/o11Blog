package com.o11eh.servicedemo.admin.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.o11eh.servicedemo.admin.enums.ResourceType;
import lombok.Data;

import java.util.List;

/**
 * @author 011eh
 * @since 2022/02/27 12:18
 */
@Data
@TableName(autoResultMap = true)
public class Permission extends BaseEntry<Permission> {
    private String name;
    private String permissionKey;
    private Integer status;
    private Long parentId;
    private Integer sort;

    private ResourceType resourceType;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private VueRouter router;

    @TableField(exist = false)
    private List<Permission> children;
}
