package com.o11eh.servicedemo.admin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.entry.Role;
import com.o11eh.servicedemo.admin.service.RoleService;
import com.o11eh.servicedemo.base.constants.BaseApiConstants;
import com.o11eh.servicedemo.base.constants.DocConstants;
import com.o11eh.servicedemo.base.controller.BaseController;
import com.o11eh.servicedemo.base.req.PageParam;
import com.o11eh.servicedemo.base.resp.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
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

    @Override
    @GetMapping(BaseApiConstants.PATH_ID)
    @ApiOperation(DocConstants.DETAIL)
    public Result detail(@PathVariable Long id) {
        Role role = roleService.getById(id);
        return Result.success(role);
    }

    @Override
    @PostMapping(BaseApiConstants.PAGE)
    @ApiOperation(DocConstants.PAGE)
    public Result page(@RequestBody PageParam param) {
        Page<Role> page = roleService.page(param.getCurrent(), param.getSize());
        return Result.success(page);
    }

    @ApiOperation(DocConstants.ADD)
    @PostMapping(BaseApiConstants.ADD)
    public Result add(@RequestBody Role role) {
        Long id = roleService.add(role);
        return Result.success(id);
    }


    @PutMapping(BaseApiConstants.UPDATE)
    @ApiOperation(DocConstants.UPDATE)
    public Result update(@RequestBody Role role) {
        Long id = roleService.updateRole(role);
        return Result.success(id);
    }

    @Override
    @DeleteMapping(BaseApiConstants.DELETE)
    @ApiOperation(DocConstants.BATCH_DELETE)
    public Result deleteBatch(@RequestBody List<Long> ids) {
        roleService.removeBatchByIds(ids);
        return Result.success();
    }
}
