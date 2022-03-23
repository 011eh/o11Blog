package com.o11eh.servicedemo.admin.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * @author 011eh
 * @since 2022/02/27 12:18
 */
@TableName("back_permission")
@Data
public class Permission extends BaseEntry<Permission> {
    private String name;
    private String permissionKey;
    private Integer status;
    private Long parentId;
    private Integer sort;
    private Integer resourceType;

    @TableField(exist = false)
    private List<Permission> children;

    public Permission(String name) {
        this.name = name;
    }
}
