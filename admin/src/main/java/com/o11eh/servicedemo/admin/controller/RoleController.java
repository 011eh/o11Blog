package com.o11eh.servicedemo.admin.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.servicebase.config.log.Log;
import com.o11eh.servicedemo.servicebase.constants.Constants;
import com.o11eh.servicedemo.servicebase.entry.PageReq;
import com.o11eh.servicedemo.servicebase.entry.Result;
import com.o11eh.servicedemo.admin.entry.Role;
import com.o11eh.servicedemo.admin.entry.vo.RoleVo;
import com.o11eh.servicedemo.admin.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 011eh
 * @since 2022-02-14
 */
@Api(tags = "角色")
@RestController
@RequestMapping("role")
@AllArgsConstructor
public class RoleController {

    RoleService roleService;

    @Log
    @ApiOperation(Constants.Doc.PAGE)
    @PostMapping(Constants.Api.PAGE)
    public Result page(@Valid @RequestBody PageReq pageReq) {
        Page<Role> page = roleService.getPage(pageReq);
        return Result.success(page);
    }

    @Log("角色新建")
    @ApiOperation(Constants.Doc.ADD)
    @PostMapping
    public Result create(@Valid @RequestBody RoleVo roleVo) {
        Role role = BeanUtil.copyProperties(roleVo, Role.class);
        roleService.create(role);
        return Result.successShowMsg();
    }

    @Log("角色更新")
    @PutMapping
    @ApiOperation(Constants.Doc.UPDATE)
    public Result update(@Valid @RequestBody Role role) {
        roleService.updateRole(role);
        return Result.successShowMsg();
    }

    @Log("角色删除")
    @DeleteMapping
    @ApiOperation(Constants.Doc.BATCH_DELETE)
    public Result deleteBatch(@RequestBody List<String> ids) {
        roleService.deleteRole(ids);
        return Result.successShowMsg();
    }
}
