package com.o11eh.o11blog.admin.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.o11blog.admin.entity.Admin;
import com.o11eh.o11blog.admin.entity.vo.AdminVo;
import com.o11eh.o11blog.admin.service.AdminService;
import com.o11eh.o11blog.servicebase.config.log.Log;
import com.o11eh.o11blog.servicebase.constants.Constants;
import com.o11eh.o11blog.servicebase.entity.PageReq;
import com.o11eh.o11blog.servicebase.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        long pageCurrent = page.getCurrent();
        long pageSize = page.getPages();
        List<Admin> data = page.getRecords();
        long total = page.getTotal();
        return Result.pageResult(pageCurrent, pageSize, total, data);
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
