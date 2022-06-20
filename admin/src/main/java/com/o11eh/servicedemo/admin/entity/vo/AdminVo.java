package com.o11eh.servicedemo.admin.entity.vo;

import com.o11eh.servicedemo.admin.config.validation.ColumnsUnique;
import com.o11eh.servicedemo.admin.config.validation.RefId;
import com.o11eh.servicedemo.servicebase.enums.Status;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ColumnsUnique(tableName = "back_admin", properties = "username")
public class AdminVo {
    @NotBlank
    private String username;

    @NotBlank
    private String nickName;

    @RefId("back_role")
    private String roleId;
    private String avatar;
    private Status status;
}