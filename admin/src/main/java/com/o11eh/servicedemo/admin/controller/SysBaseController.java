package com.o11eh.servicedemo.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.entry.*;
import com.o11eh.servicedemo.admin.service.OsService;
import com.o11eh.servicedemo.admin.service.PermissionService;
import com.o11eh.servicedemo.admin.service.RoleService;
import com.o11eh.servicedemo.admin.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = "系统基础配置")
@RestController
@RequestMapping("sysBase")
public class SysBaseController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private OsService osService;

    @Autowired
    private SysLogService sysLogService;

    @ApiOperation("权限Dto列表")
    @GetMapping("permissionDto")
    public Result permissionDto() {
        List<Permission> permissionList = permissionService.dtoList();
        return Result.success(permissionList);
    }

    @ApiOperation("权限授予列表")
    @GetMapping("permissionTree")
    public Result permissionDtoTree() {
        List<Permission> treeVoList = permissionService.treeDtoList();
        return Result.success(treeVoList);
    }

    @ApiOperation("角色Dto列表")
    @GetMapping("roleDto")
    public Result roleDto() {
        List<Role> dtoList = roleService.dtoList();
        return Result.success(dtoList);
    }

    @ApiOperation("头像上传")
    @PostMapping("uploadAvatar")
    public Result uploadAvatar(MultipartFile file) {
        String url = osService.uploadFile(file);
        return Result.success(url);
    }

    @ApiOperation("操作日志")
    @PostMapping("sysLogPage")
    public Result getSysLog(@RequestBody PageReq req) {
        Page<SysLog> page = sysLogService.page(req.getCurrent(), req.getSize());
        return Result.success(page);
    }
}
