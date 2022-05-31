package com.o11eh.servicedemo.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.config.log.Log;
import com.o11eh.servicedemo.admin.entry.*;
import com.o11eh.servicedemo.admin.entry.vo.SysLogPageReq;
import com.o11eh.servicedemo.admin.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Api(tags = "系统基础配置")
@RestController
@RequestMapping("sysBase")
@AllArgsConstructor
public class SysBaseController {

    private PermissionService permissionService;
    private RoleService roleService;
    private OsService osService;
    private SysLogService sysLogService;
    private SysParamService sysParamService;
    private AdminService adminService;
    private ApiMatcherService apiMatcherService;

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

    @ApiOperation("管理员Dto列表")
    @GetMapping("adminDto")
    public Result adminDto() {
        List<Admin> list = adminService.dtoList();
        return Result.success(list);
    }

    @Log("头像上传")
    @ApiOperation("头像上传")
    @PostMapping("uploadAvatar")
    public Result uploadAvatar(MultipartFile file) {
        String url = osService.uploadFile(file);
        return Result.success(url);
    }

    @ApiOperation("操作日志分页查询")
    @PostMapping("sysLogPage")
    public Result getSysLogPage(@RequestBody SysLogPageReq req) {
        Page<SysLog> page = sysLogService.page(req);
        return Result.success(page);
    }

    @ApiOperation("系统参数分页")
    @PostMapping("sysParam/page")
    public Result getSysParamPage(@RequestBody PageReq req) {
        Page<SysParam> page = sysParamService.getPage(req);
        return Result.success(page);
    }

    @ApiOperation("系统参数创建")
    @PostMapping("sysParam")
    public Result createParam(@RequestBody SysParam param) {
        sysParamService.save(param);
        return Result.successShowMsg();
    }

    @ApiOperation("系统参数更新")
    @PutMapping("sysParam")
    public Result updateParam(@RequestBody SysParam param) {
        sysParamService.updateById(param);
        return Result.successShowMsg();
    }

    @ApiOperation("系统参数删除")
    @DeleteMapping("sysParam")
    public Result deleteParam(@RequestBody List<Long> ids) {
        sysParamService.removeBatchByIds(ids);
        return Result.successShowMsg();
    }

    @ApiOperation("接口信息")
    @GetMapping("allUrl")
    public Result getAllUrls() {
        return Result.success(apiMatcherService.getAllUrl());
    }
}
