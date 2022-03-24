package com.o11eh.servicedemo.admin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.constants.Constants;
import com.o11eh.servicedemo.admin.entry.PageParam;
import com.o11eh.servicedemo.admin.entry.Permission;
import com.o11eh.servicedemo.admin.entry.Result;
import com.o11eh.servicedemo.admin.entry.Role;
import com.o11eh.servicedemo.admin.service.PermissionService;
import com.o11eh.servicedemo.admin.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 011eh
 * @since 2022-02-14
 */
@RestController
@RequestMapping("/role")
@Api(tags = "角色")
public class RoleController extends BaseController {
    @Autowired
    RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    @GetMapping(Constants.Api.PATH_ID)
    @ApiOperation(Constants.Doc.DETAIL)
    public Result detail(@PathVariable Long id) {
        Role role = roleService.getById(id);
        List<Permission> keys = permissionService.getPermissionByRoleId(id);
        role.setPermissionKeys(null);
        return Result.success(role);
    }

    @Override
    @PostMapping(Constants.Api.PAGE)
    @ApiOperation(Constants.Doc.PAGE)
    public Result page(@RequestBody PageParam param) {
        Page<Role> page = roleService.page(param.getCurrent(), param.getSize());
        return Result.success(page);
    }

    @ApiOperation(Constants.Doc.ADD)
    @PostMapping(Constants.Api.ADD)
    public Result add(@RequestBody Role role) {
        Long id = roleService.add(role);
        return Result.success(id);
    }


    @PutMapping(Constants.Api.UPDATE)
    @ApiOperation(Constants.Doc.UPDATE)
    public Result update(@RequestBody Role role) {
        Long id = roleService.updateRole(role);
        return Result.success(id);
    }

    @Override
    @DeleteMapping(Constants.Api.DELETE)
    @ApiOperation(Constants.Doc.BATCH_DELETE)
    public Result deleteBatch(@RequestBody List<Long> ids) {
        roleService.removeBatchByIds(ids);
        return Result.success();
    }
}
