package com.o11eh.servicedemo.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.entry.Permission;
import com.o11eh.servicedemo.admin.service.PermissionService;
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
    @GetMapping(BaseApiConstants.PATH_ID)
    @ApiOperation(DocConstants.DETAIL)
    public Result detail(@PathVariable Long id) {
        Permission permission = permissionService.getById(id);
        return Result.success(permission);
    }

    @Override
    @PostMapping(BaseApiConstants.PAGE)
    @ApiOperation(DocConstants.PAGE)
    public Result page(@RequestBody PageParam param, BindingResult result) {
        Page<Permission> page = permissionService.page(param.getCurrent(), param.getSize());
        return Result.success(page);
    }

    @PostMapping(BaseApiConstants.ADD)
    @ApiOperation(DocConstants.ADD)
    public Result add(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.success(permission.getId());
    }

    @PutMapping(BaseApiConstants.UPDATE)
    @ApiOperation(DocConstants.UPDATE)
    public Result update(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.success(permission.getId());
    }


    @Override
    @ApiOperation(DocConstants.BATCH_DELETE)
    @DeleteMapping(BaseApiConstants.DELETE)
    public Result deleteBatch(@RequestBody List<Long> ids) {
        permissionService.removeBatchByIds(ids);
        return Result.success();
    }
}
