package com.o11eh.servicedemo.admin.controller;

import com.o11eh.servicedemo.admin.entry.Permission;
import com.o11eh.servicedemo.admin.entry.Result;
import com.o11eh.servicedemo.admin.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "系统基础配置")
@RestController
@RequestMapping("sysConfig")
public class SysConfigController {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("权限Dto")
    @GetMapping("permissionDto")
    public Result permissionDto() {
        List<Permission> permissionList = permissionService.getParentSelect();
        return Result.success(permissionList);
    }
}
