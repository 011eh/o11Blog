package com.o11eh.servicedemo.admin.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.o11eh.servicedemo.admin.enums.Status;
import io.swagger.annotations.ApiModel;
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
public class Admin extends BaseEntry {

    @NotBlank
    private String username;

    private String nickName;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String roleId;
    private String avatar;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Status status;

    @JsonUnwrapped(suffix = "Role")
    @TableField(exist = false)
    private Role role;
}
