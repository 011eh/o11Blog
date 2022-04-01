package com.o11eh.servicedemo.admin.controller;

import com.o11eh.servicedemo.admin.entry.Permission;
import com.o11eh.servicedemo.admin.entry.Result;
import com.o11eh.servicedemo.admin.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("sysConfig")
public class SysConfigController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("permissionSelect")
    public Result permissionDropDown() {
        List<Permission> permissionList = permissionService.getParentSelect();
        return Result.success(permissionList);
    }
}
