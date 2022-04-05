package com.o11eh.servicedemo.admin.entry.vo;

import com.o11eh.servicedemo.admin.entry.RouterInfo;
import com.o11eh.servicedemo.admin.enums.ResourceType;
import com.o11eh.servicedemo.admin.enums.Status;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PermissionVo {

    @NotNull
    private String name;

    @NotNull
    private String permissionKey;
    private Status status;

    @NotNull
    private String parentId;
    private Integer sort;

    @NotNull
    private ResourceType resourceType;
    private RouterInfo routerInfo;
}
