package com.o11eh.commons.entry;

import com.baomidou.mybatisplus.annotation.*;
import com.o11eh.servicedemo.base.entry.BaseEntry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <p>
 * 管理员
 * </p>
 *
 * @author 011eh
 * @since 2022-02-14
 */
@Getter
@Setter
@TableName("back_admin")
@ApiModel(value = "Admin对象", description = "管理员")
public class Admin extends BaseEntry {

    @ApiModelProperty("用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("角色Id")
    @TableField("role_id")
    private Long roleId;

    @ApiModelProperty("头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("状态")
    @TableField("status")
    private Integer status;
}
