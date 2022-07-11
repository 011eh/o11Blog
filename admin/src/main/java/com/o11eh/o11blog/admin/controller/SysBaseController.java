package com.o11eh.o11blog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.o11blog.admin.entity.Admin;
import com.o11eh.o11blog.admin.entity.Permission;
import com.o11eh.o11blog.admin.entity.Role;
import com.o11eh.o11blog.admin.entity.SysParam;
import com.o11eh.o11blog.admin.entity.vo.SysLogPageReq;
import com.o11eh.o11blog.admin.service.AdminService;
import com.o11eh.o11blog.admin.service.OsService;
import com.o11eh.o11blog.admin.service.PermissionService;
import com.o11eh.o11blog.admin.service.RoleService;
import com.o11eh.o11blog.admin.service.SysLogService;
import com.o11eh.o11blog.admin.service.SysParamService;
import com.o11eh.o11blog.servicebase.config.log.Log;
import com.o11eh.o11blog.servicebase.entity.PageReq;
import com.o11eh.o11blog.servicebase.entity.Result;
import com.o11eh.o11blog.servicebase.entity.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    private RequestMappingHandlerMapping handlerMapping;

    @Log
    @ApiOperation("权限Dto列表")
    @GetMapping("permissionDto")
    public Result permissionDto() {
        List<Permission> permissionList = permissionService.dtoList();
        return Result.success(permissionList);
    }

    @Log
    @ApiOperation("权限授予列表")
    @GetMapping("permissionTree")
    public Result permissionDtoTree() {
        List<Permission> treeVoList = permissionService.treeDtoList();
        return Result.success(treeVoList);
    }

    @Log
    @ApiOperation("角色Dto列表")
    @GetMapping("roleDto")
    public Result roleDto() {
        List<Role> dtoList = roleService.dtoList();
        return Result.success(dtoList);
    }

    @Log
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

    @Log
    @ApiOperation("操作日志分页查询")
    @PostMapping("sysLogPage")
    public Result getSysLogPage(@RequestBody SysLogPageReq req) {
        Page<SysLog> page = sysLogService.page(req);
        long pageCurrent = page.getCurrent();
        long pageSize = page.getPages();
        List<SysLog> data = page.getRecords();
        long total = page.getTotal();
        return Result.pageResult(pageCurrent, pageSize, total, data);
    }

    @Log
    @ApiOperation("系统参数分页")
    @PostMapping("sysParam/page")
    public Result getSysParamPage(@RequestBody PageReq req) {
        Page<SysParam> page = sysParamService.getPage(req);
        long pageCurrent = page.getCurrent();
        long pageSize = page.getPages();
        List<SysParam> data = page.getRecords();
        long total = page.getTotal();
        return Result.pageResult(pageCurrent, pageSize, total, data);
    }

    @Log
    @ApiOperation("系统参数创建")
    @PostMapping("sysParam")
    public Result createParam(@RequestBody SysParam param) {
        sysParamService.save(param);
        return Result.successShowMsg();
    }

    @Log
    @ApiOperation("系统参数更新")
    @PutMapping("sysParam")
    public Result updateParam(@RequestBody SysParam param) {
        sysParamService.updateById(param);
        return Result.successShowMsg();
    }

    @Log
    @ApiOperation("系统参数删除")
    @DeleteMapping("sysParam")
    public Result deleteParam(@RequestBody List<Long> ids) {
        sysParamService.removeBatchByIds(ids);
        return Result.successShowMsg();
    }

    @Log
    @ApiOperation("接口信息")
    @GetMapping("allUrl")
    public Result getAllUrl() {
        Set<String> urls = handlerMapping.getHandlerMethods().entrySet().stream()
                .filter(entry -> Pattern.matches("com\\.o11eh\\..*", entry.getValue().toString()))
                .map(entry -> entry.getKey().getPatternsCondition().getPatterns())
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(s -> s)).collect(Collectors.toCollection(LinkedHashSet::new));
        return Result.success(urls);
    }
}
