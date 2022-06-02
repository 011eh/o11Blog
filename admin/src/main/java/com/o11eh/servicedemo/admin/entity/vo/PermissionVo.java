package com.o11eh.servicedemo.admin.entity.vo;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.o11eh.servicedemo.admin.config.validation.RefId;
import com.o11eh.servicedemo.admin.entity.Permission;
import com.o11eh.servicedemo.servicebase.enums.ResourceType;
import com.o11eh.servicedemo.servicebase.enums.Status;
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

    @RefId("back_permission")
    private String parentId;
    private Integer sort;

    @NotNull
    private ResourceType resourceType;

    @JsonUnwrapped
    private Permission.RouterInfo routerInfo;
}
