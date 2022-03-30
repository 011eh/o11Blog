package com.o11eh.servicedemo.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.constants.Constants;
import com.o11eh.servicedemo.admin.entry.BaseEntry;
import com.o11eh.servicedemo.admin.entry.PageParam;
import com.o11eh.servicedemo.admin.entry.Permission;
import com.o11eh.servicedemo.admin.entry.Result;
import com.o11eh.servicedemo.admin.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result detail(@PathVariable String id) {
        Permission permission = permissionService.getOne(Wrappers.<Permission>lambdaQuery()
                .select(Permission.class, tableFieldInfo -> {
                    return !tableFieldInfo.getProperty().equals("status");
                }).eq(BaseEntry::getId, id));
        return Result.success(permission);
    }

    @Override
    @PostMapping(Constants.Api.PAGE)
    @ApiOperation(Constants.Doc.PAGE)
    public Result list(@RequestBody PageParam param) {
        Page<Permission> page = permissionService.page(param.getCurrent(), param.getSize());
        return Result.success(page);
    }

    @PostMapping
    @ApiOperation(Constants.Doc.ADD)
    public Result add(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.success(permission.getId());
    }

    @PutMapping
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
    @GetMapping("list")
    public Result getPermissions() {
        List<Permission> permissions = permissionService.getPermissionList();
        return Result.success(permissions);
    }
}
