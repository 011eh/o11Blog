package com.o11eh.servicedemo.admin.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.o11eh.servicedemo.base.entry.BaseEntry;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 011eh
 * @since 2022-02-14
 */
@Data
@TableName("back_role")
@ApiModel(value = "Role对象", description = "角色")
public class Role extends BaseEntry<Role> {

    private static final long serialVersionUID = 1L;

    private String name;
    private String roleKey;
    private String permissionIds;
    private String summary;
    @TableField(exist = false)
    private List<Long> permissionKeyList;
}
