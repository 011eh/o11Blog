package com.o11eh.servicedemo.admin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.constants.ApiConst;
import com.o11eh.servicedemo.base.validation.groups.Add;
import com.o11eh.servicedemo.commons.entry.Admin;
import com.o11eh.servicedemo.commons.service.AdminService;
import com.o11eh.servicedemo.servicebase.constants.BaseApiConst;
import com.o11eh.servicedemo.servicebase.resp.Result;
import com.o11eh.servicedemo.servicebase.utils.ValidUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 管理员 前端控制器
 * </p>
 *
 * @author 011eh
 * @since 2022-02-14
 */
@RestController
@RequestMapping(ApiConst.ADMIN)
@Api(tags = "管理员")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping(BaseApiConst.ADD)
    @ApiOperation("增加管理员")
    public Result add(@Validated(Add.class) @RequestBody Admin admin, BindingResult result) {
        ValidUtil.checkParam(result);
        Long id = adminService.add(admin);
        return Result.success(id);
    }

    @ApiOperation("管理员分页查询")
    @GetMapping(BaseApiConst.CURRENT_SIZE)
    public Result page(@PathVariable Long current, @PathVariable Long size) {
        Page<Admin> page = adminService.getPage(current, size);
        return Result.success(page);
    }

    @ApiOperation("管理员更新")
    @PutMapping(BaseApiConst.UPDATE)
    public void update() {

    }
}