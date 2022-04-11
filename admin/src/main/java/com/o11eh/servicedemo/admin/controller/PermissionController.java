package com.o11eh.servicedemo.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.o11eh.servicedemo.admin.constants.Constants;
import com.o11eh.servicedemo.admin.entry.Permission;
import com.o11eh.servicedemo.admin.entry.Result;
import com.o11eh.servicedemo.admin.entry.vo.PermissionVo;
import com.o11eh.servicedemo.admin.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @ApiOperation("授权列表")
    @GetMapping("list")
    public Result list() {
        List<Permission> permissions = permissionService.getPermissionList();
        return Result.success(permissions);
    }

    @GetMapping(Constants.Api.PATH_ID)
    @ApiOperation(Constants.Doc.DETAIL)
    public Result detail(@PathVariable String id) {
        Permission permission = permissionService.detail(id);
        return Result.success(permission);
    }

    @PostMapping
    @ApiOperation(Constants.Doc.ADD)
    public Result create(@RequestBody PermissionVo vo) {
        Permission permission = BeanUtil.copyProperties(vo, Permission.class);
        permissionService.save(permission);
        return Result.success(permission.getId());
    }

    @PutMapping
    @ApiOperation(Constants.Doc.UPDATE)
    public Result update(@Valid @RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.success(permission.getId());
    }


    @ApiOperation(Constants.Doc.BATCH_DELETE)
    @DeleteMapping
    public Result delete(@RequestBody List<Long> ids) {
        permissionService.removeBatchByIds(ids);
        return Result.success();
    }

    @GetMapping("granted/{roleId}")
    public Result getRolePermissions(@PathVariable String roleId) {
        List<String> permissionIds = permissionService.getPermissionIdsGranted(roleId);
        return Result.success(permissionIds);
    }
}
