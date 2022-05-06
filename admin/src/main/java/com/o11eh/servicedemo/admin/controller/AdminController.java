package com.o11eh.servicedemo.admin.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.constants.Constants;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.entry.PageReq;
import com.o11eh.servicedemo.admin.entry.Result;
import com.o11eh.servicedemo.admin.entry.vo.AdminVo;
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
    public Result page(@Valid @RequestBody PageReq param) {
        Page<Admin> page = adminService.page(param);
        return Result.success(page);
    }

    @PostMapping
    @ApiOperation(Constants.Doc.ADD)
    public Result create(@Valid @RequestBody AdminVo adminVo) {
        Admin admin = BeanUtil.copyProperties(adminVo, Admin.class);
        adminService.create(admin);
        return Result.successShowMsg();
    }

    @ApiOperation(Constants.Doc.UPDATE)
    @PutMapping
    public Result update(@Valid @RequestBody Admin admin) {
        adminService.update(admin);
        return Result.successShowMsg();
    }

    @DeleteMapping
    @ApiOperation(Constants.Doc.BATCH_DELETE)
    public Result delete(@RequestBody List<Long> ids) {
        adminService.removeBatchByIds(ids);
        return Result.successShowMsg();
    }
}
