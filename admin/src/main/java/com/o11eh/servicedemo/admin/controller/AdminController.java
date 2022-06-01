package com.o11eh.servicedemo.admin.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.servicebase.config.log.Log;
import com.o11eh.servicedemo.servicebase.constants.Constants;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.servicebase.entry.PageReq;
import com.o11eh.servicedemo.servicebase.entry.Result;
import com.o11eh.servicedemo.admin.entry.vo.AdminVo;
import com.o11eh.servicedemo.admin.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class AdminController {

    private AdminService adminService;

    @Log
    @ApiOperation(Constants.Doc.PAGE)
    @PostMapping(Constants.Api.PAGE)
    public Result page(@Valid @RequestBody PageReq param) {
        Page<Admin> page = adminService.page(param);
        return Result.success(page);
    }

    @Log("管理员新建")
    @PostMapping
    @ApiOperation(Constants.Doc.ADD)
    public Result create(@Valid @RequestBody AdminVo adminVo) {
        Admin admin = BeanUtil.copyProperties(adminVo, Admin.class);
        adminService.create(admin);
        return Result.successShowMsg();
    }

    @Log("管理员更新")
    @ApiOperation(Constants.Doc.UPDATE)
    @PutMapping
    public Result update(@Valid @RequestBody Admin admin) {
        adminService.update(admin);
        return Result.successShowMsg();
    }

    @Log("管理员删除")
    @DeleteMapping
    @ApiOperation(Constants.Doc.BATCH_DELETE)
    public Result delete(@RequestBody List<Long> ids) {
        adminService.removeBatchByIds(ids);
        return Result.successShowMsg();
    }

    @Log
    @ApiOperation("重置密码")
    @PostMapping("resetPassword")
    public Result resetPassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        adminService.resetPassword(oldPassword, newPassword);
        return Result.success();
    }

    @Log
    @ApiOperation("重置密码为默认密码")
    @PostMapping("resetPassword/{userId}")
    public Result resetToDefaultPassword(@PathVariable String userId) {
        adminService.resetToDefaultPassword(userId);
        return Result.successShowMsg();
    }
}
