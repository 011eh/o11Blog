package com.o11eh.servicedemo.admin.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.server.HttpServerRequest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.constants.ApiConstants;
import com.o11eh.servicedemo.base.constants.BaseApiConstants;
import com.o11eh.servicedemo.base.resp.Result;
import com.o11eh.servicedemo.base.validation.groups.Add;
import com.o11eh.servicedemo.base.validation.groups.Update;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.service.AdminService;
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
@RequestMapping(ApiConstants.ADMIN)
@Api(tags = "管理员")
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping(BaseApiConstants.ADD)
    @ApiOperation("增加管理员")
    public Result add(@Validated(Add.class) @RequestBody Admin admin, BindingResult result) {
        ValidUtil.checkParam(result);
        Long id = adminService.add(admin);
        return Result.success(id);
    }

    @ApiOperation("管理员分页查询")
    @GetMapping(BaseApiConstants.CURRENT_SIZE)
    public Result page(@PathVariable Long current, @PathVariable Long size) {
        Page<Admin> page = adminService.getPage(current, size);
        return Result.success(page);
    }

    @ApiOperation("管理员更新")
    @PutMapping(BaseApiConstants.UPDATE)
    public void update(@Validated(Update.class) @RequestBody Admin admin, BindingResult result) {
        ValidUtil.checkParam(result);
    }

    @PostMapping("/login")
    public Result login(HttpServerRequest request, @RequestParam String username, @RequestParam String password) {
        if (StrUtil.hasEmpty(username, password)) {
            return Result.error("帐号或密码为空");
        }
        return Result.success();
    }
}