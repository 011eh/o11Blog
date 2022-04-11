package com.o11eh.servicedemo.admin.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.constants.Constants;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.entry.PageReq;
import com.o11eh.servicedemo.admin.entry.Result;
import com.o11eh.servicedemo.admin.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(Constants.Doc.PAGE)
    @PostMapping(Constants.Api.PAGE)
    public Result page(@RequestBody @Valid PageReq param) {
        Page<Admin> page = adminService.page(param);
        return Result.success(page);
    }

    @ApiOperation(Constants.Doc.DETAIL)
    @GetMapping(Constants.Api.PATH_ID)
    public Result detail(@PathVariable String id) {
        Admin admin = adminService.getById(id);
        return Result.success(admin);
    }


    @PostMapping(Constants.Api.ADD)
    @ApiOperation(Constants.Doc.ADD)
    public Result add(@Valid @RequestBody Admin admin) {
        String id = adminService.create(admin);
        return Result.success(id);
    }

    @ApiOperation(Constants.Doc.UPDATE)
    @PutMapping(Constants.Api.UPDATE)
    public Result update(@Valid @RequestBody Admin admin) {
        String id = adminService.update(admin);
        return Result.success(id);
    }

    @DeleteMapping(Constants.Api.DELETE)
    @ApiOperation(Constants.Doc.BATCH_DELETE)
    public Result deleteBatch(@RequestBody List<Long> ids) {
        adminService.removeBatchByIds(ids);
        return Result.success();
    }
}
