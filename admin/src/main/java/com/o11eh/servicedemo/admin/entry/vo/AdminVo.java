package com.o11eh.servicedemo.admin.entry.vo;

import com.o11eh.servicedemo.admin.enums.Status;
import com.o11eh.servicedemo.validation.ColumnsUnique;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ColumnsUnique(tableName = "back_admin", properties = "username")
public class AdminVo {
    @NotBlank
    private String username;

    @NotBlank
    private String nickName;

    private String roleId;
    private String avatar;
    private Status status;
}
