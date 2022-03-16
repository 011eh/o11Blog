package com.o11eh.servicedemo.admin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.service.AdminService;
import com.o11eh.servicedemo.servicebase.constants.BaseApiConstants;
import com.o11eh.servicedemo.servicebase.constants.DocConstants;
import com.o11eh.servicedemo.base.controller.BaseController;
import com.o11eh.servicedemo.base.req.PageParam;
import com.o11eh.servicedemo.base.resp.Result;
import com.o11eh.servicedemo.base.validation.groups.Update;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping(BaseApiConstants.ADMIN)
@Api(value = "管理员", tags = "管理员")
@RestController
public class AdminController extends BaseController {

    @Autowired
    private AdminService adminService;

    @Override
    @ApiOperation(DocConstants.DETAIL)
    @GetMapping(BaseApiConstants.PATH_ID)
    public Result detail(@PathVariable Long id) {
        Admin admin = adminService.getById(id);
        return Result.success(admin);
    }

    @Override
    @ApiOperation(DocConstants.PAGE)
    @PostMapping(BaseApiConstants.PAGE)
    public Result page(@RequestBody @Valid PageParam param) {
        Page<Admin> page = adminService.page(param.getCurrent(), param.getSize());
        return Result.success(page);
    }


    @PostMapping(BaseApiConstants.ADD)
    @ApiOperation(DocConstants.ADD)
    public Result add(@Valid @RequestBody Admin admin) {
        Long id = adminService.add(admin);
        return Result.success(id);
    }

    @ApiOperation(DocConstants.UPDATE)
    @PutMapping(BaseApiConstants.UPDATE)
    public Result update(@Validated(Update.class) @RequestBody Admin admin) {
        Long id = adminService.updateAdmin(admin);
        return Result.success(id);
    }

    @Override
    @DeleteMapping(BaseApiConstants.DELETE)
    @ApiOperation(DocConstants.BATCH_DELETE)
    public Result deleteBatch(@RequestBody List<Long> ids) {
        adminService.removeBatchByIds(ids);
        return Result.success();
    }
}