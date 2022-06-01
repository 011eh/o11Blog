package com.o11eh.servicedemo.admin.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.o11eh.servicedemo.admin.config.validation.ColumnsUnique;
import com.o11eh.servicedemo.admin.config.validation.RefId;
import com.o11eh.servicedemo.servicebase.enums.Status;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

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
@ColumnsUnique(tableName = "back_admin", properties = "username")
public class Admin extends BaseEntry {

    private String username;
    private String nickName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @RefId("back_role")
    private String roleId;

    @TableField(exist = false)
    private String roleName;
    private String avatar;
    private Status status;
    private String lastLoginIp;
    private LocalDateTime lastLoginTime;
}
