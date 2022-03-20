package com.o11eh.servicedemo.admin.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonView;
import com.o11eh.servicedemo.base.entry.BaseEntry;
import com.o11eh.servicedemo.base.utils.jackson.Views;
import com.o11eh.servicedemo.base.validation.groups.Add;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 管理员
 * </p>
 *
 * @author 011eh
 * @since 2022-02-14
 */
@Data
@TableName("back_admin")
@ApiModel(value = "Admin对象", description = "管理员")
public class Admin extends BaseEntry<Admin> {

    @NotBlank(groups = Add.class)
    private String username;

    private String nickName;

    @NotBlank(groups = Add.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Long roleId;
    private String avatar;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty("状态")
    private Integer status;

    @JsonUnwrapped(prefix = "role")
    @TableField(exist = false)
    private Role role;
}
