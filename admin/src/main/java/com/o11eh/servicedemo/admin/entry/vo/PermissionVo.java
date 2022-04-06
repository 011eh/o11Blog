package com.o11eh.servicedemo.admin.entry.vo;

import com.o11eh.servicedemo.admin.entry.RouterInfo;
import com.o11eh.servicedemo.admin.enums.ResourceType;
import com.o11eh.servicedemo.admin.enums.Status;
import com.o11eh.servicedemo.validation.RefId;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PermissionVo {

    @NotEmpty
    private String name;

    @NotEmpty
    private String permissionKey;
    private Status status;

    @RefId(tableName = "back_permission")
    private String parentId;
    private Integer sort;

    @NotNull
    private ResourceType resourceType;
    private RouterInfo routerInfo;
}
