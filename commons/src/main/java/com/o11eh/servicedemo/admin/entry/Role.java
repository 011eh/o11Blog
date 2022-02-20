package com.o11eh.servicedemo.admin.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.o11eh.servicedemo.base.entry.BaseEntry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author 011eh
 * @since 2022-02-14
 */
@Getter
@Setter
@TableName("back_role")
@ApiModel(value = "Role对象", description = "角色")
public class Role extends BaseEntry<Role> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色名")
    @TableField("name")
    private String name;

    @ApiModelProperty("操作菜单Id列表")
    @TableField("menu_ids")
    private String menuIds;

    @ApiModelProperty("状态")
    @TableField("status")
    private Integer status;
}
