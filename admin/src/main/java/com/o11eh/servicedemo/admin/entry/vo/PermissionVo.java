package com.o11eh.servicedemo.admin.entry.vo;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.o11eh.servicedemo.admin.config.validation.RefId;
import com.o11eh.servicedemo.admin.entry.RouterInfo;
import com.o11eh.servicedemo.admin.enums.ResourceType;
import com.o11eh.servicedemo.admin.enums.Status;
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
    private RouterInfo routerInfo;
}
