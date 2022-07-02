package com.o11eh.o11blog.admin.entity.vo;

import com.o11eh.o11blog.admin.config.validation.ColumnsUnique;
import com.o11eh.o11blog.admin.config.validation.RefId;
import com.o11eh.o11blog.servicebase.enums.Status;
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
