package com.o11eh.servicedemo.admin.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.service.AdminService;
import com.o11eh.servicedemo.admin.entry.PageParam;
import com.o11eh.servicedemo.admin.entry.Result;
import com.o11eh.servicedemo.admin.utils.Update;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.o11eh.servicedemo.admin.constants.Constants;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 管理员 前端控制器
 * </p>
 *
 * @author 011eh
 * @since 2022-02-14
 */
@RequestMapping("admin")
@Api(value = "管理员", tags = "管理员")
@RestController
public class AdminController extends BaseController {

    @Autowired
    private AdminService adminService;

    @Override
    @ApiOperation(Constants.Doc.DETAIL)
    @GetMapping(Constants.Api.PATH_ID)
    public Result detail(@PathVariable String id) {
        Admin admin = adminService.getById(id);
        return Result.success(admin);
    }

    @Override
    @ApiOperation(Constants.Doc.PAGE)
    @SaCheckPermission("admin:list")
    @PostMapping(Constants.Api.PAGE)
    public Result list(@RequestBody @Valid PageParam param) {
        Page<Admin> page = adminService.page(param.getCurrent(), param.getSize());
        return Result.success(page);
    }


    @PostMapping(Constants.Api.ADD)
    @ApiOperation(Constants.Doc.ADD)
    public Result add(@Valid @RequestBody Admin admin) {
        String id = adminService.add(admin);
        return Result.success(id);
    }

    @ApiOperation(Constants.Doc.UPDATE)
    @PutMapping(Constants.Api.UPDATE)
    public Result update(@Validated(Update.class) @RequestBody Admin admin) {
        String id = adminService.updateAdmin(admin);
        return Result.success(id);
    }

    @Override
    @DeleteMapping(Constants.Api.DELETE)
    @ApiOperation(Constants.Doc.BATCH_DELETE)
    public Result deleteBatch(@RequestBody List<Long> ids) {
        adminService.removeBatchByIds(ids);
        return Result.success();
    }
}
