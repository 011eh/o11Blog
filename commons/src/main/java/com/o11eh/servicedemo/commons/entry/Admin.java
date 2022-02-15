package com.o11eh.servicedemo.commons.entry;

import com.baomidou.mybatisplus.annotation.TableName;
import com.o11eh.servicedemo.base.entry.BaseEntry;
import com.o11eh.servicedemo.base.validation.groups.Add;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

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
public class Admin extends BaseEntry<Admin> {

    @ApiModelProperty("用户名")
    @NotBlank(groups = Add.class)
    private String username;

    @NotBlank(groups = Add.class)
    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("角色Id")
    private Long roleId;

    @ApiModelProperty("头像")
    private String avatar;

    @Null
    @ApiModelProperty("状态")
    private Integer status;
}
