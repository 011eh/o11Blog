package com.o11eh.servicedemo.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.entry.Permission;
import com.o11eh.servicedemo.admin.service.PermissionService;
import com.o11eh.servicedemo.admin.entry.PageParam;
import com.o11eh.servicedemo.admin.entry.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.o11eh.servicedemo.admin.constants.Constants;

import java.util.List;

/**
 * @author 011eh
 * @since 2022/02/27 13:30
 */
@RestController
@Api(tags = "权限")
@RequestMapping("permission")
public class PermissionController extends BaseController {
    @Autowired
    private PermissionService permissionService;

    @Override
    @GetMapping(Constants.Api.PATH_ID)
    @ApiOperation(Constants.Doc.DETAIL)
    public Result detail(@PathVariable Long id) {
        Permission permission = permissionService.getById(id);
        return Result.success(permission);
    }

    @Override
    @PostMapping(Constants.Api.PAGE)
    @ApiOperation(Constants.Doc.PAGE)
    public Result page(@RequestBody PageParam param) {
        Page<Permission> page = permissionService.page(param.getCurrent(), param.getSize());
        return Result.success(page);
    }

    @PostMapping(Constants.Api.ADD)
    @ApiOperation(Constants.Doc.ADD)
    public Result add(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.success(permission.getId());
    }

    @PutMapping(Constants.Api.UPDATE)
    @ApiOperation(Constants.Doc.UPDATE)
    public Result update(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.success(permission.getId());
    }


    @Override
    @ApiOperation(Constants.Doc.BATCH_DELETE)
    @DeleteMapping(Constants.Api.DELETE)
    public Result deleteBatch(@RequestBody List<Long> ids) {
        permissionService.removeBatchByIds(ids);
        return Result.success();
    }

    @ApiOperation("授权列表")
    @GetMapping("permissionList")
    public Result getPermissions() {
        List<Permission> permissions = permissionService.getPermissions();
        return Result.success(permissions);
    }
}
