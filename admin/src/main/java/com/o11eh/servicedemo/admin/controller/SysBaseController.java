package com.o11eh.servicedemo.admin.controller;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import com.o11eh.servicedemo.admin.entry.Permission;
import com.o11eh.servicedemo.admin.entry.Result;
import com.o11eh.servicedemo.admin.entry.Role;
import com.o11eh.servicedemo.admin.service.PermissionService;
import com.o11eh.servicedemo.admin.service.RoleService;
import com.o11eh.servicedemo.admin.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    private UploadService uploadService;

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
        uploadService.uploadFile(file);
        return Result.success();
    }

}
