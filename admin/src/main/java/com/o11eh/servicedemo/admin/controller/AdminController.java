package com.o11eh.servicedemo.admin.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.server.HttpServerRequest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.constants.ApiConstants;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.service.AdminService;
import com.o11eh.servicedemo.base.constants.BaseApiConstants;
import com.o11eh.servicedemo.base.constants.DocConstants;
import com.o11eh.servicedemo.base.controller.BaseController;
import com.o11eh.servicedemo.base.exception.BusinessException;
import com.o11eh.servicedemo.base.req.PageParam;
import com.o11eh.servicedemo.base.resp.Result;
import com.o11eh.servicedemo.base.validation.groups.Add;
import com.o11eh.servicedemo.base.validation.groups.Update;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 管理员 前端控制器
 * </p>
 *
 * @author 011eh
 * @since 2022-02-14
 */
@RequestMapping(ApiConstants.ADMIN)
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
    public Result page(@RequestBody @Validated PageParam param, BindingResult result) {
        Page<Admin> page = adminService.page(param.getCurrent(), param.getSize());
        return Result.success(page);
    }


    @PostMapping(BaseApiConstants.ADD)
    @ApiOperation(DocConstants.ADD)
    public Result add(@Validated(Add.class) @RequestBody Admin admin, BindingResult result) {
        Long id = adminService.add(admin);
        return Result.success(id);
    }

    @ApiOperation(DocConstants.UPDATE)
    @PutMapping(BaseApiConstants.UPDATE)
    public Result update(@Validated(Update.class) @RequestBody Admin admin, BindingResult result) {
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

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(HttpServerRequest request, @RequestParam String username, @RequestParam String password) {
        if (StrUtil.hasEmpty(username, password)) {
            throw BusinessException.e("用户名或密码为空");
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken authenticationToken = new UsernamePasswordToken(username, password);
        try {
            subject.login(authenticationToken);
        } catch (DisabledAccountException | UnknownAccountException e) {
            throw BusinessException.e(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            throw BusinessException.e("用户或密码错误");
        }
        return Result.success();
    }

    @GetMapping("logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success();
    }

    @GetMapping("/toLogin")
    public Result toLogin() {
        throw BusinessException.e("您尚未登录");
    }

    @GetMapping("/unauthorized")
    public Result unauthorized() {
        throw BusinessException.e("无相关权限进行操作");
    }


    @GetMapping("anon")
    public Result anon() {
        return Result.success();
    }

    @GetMapping("authc")
    public Result authc() {
        return Result.success();
    }

    @GetMapping("perm1")
    public Result perm1() {
        return Result.success();
    }

    @GetMapping("perm2")
    public Result perm2() {
        return Result.success();
    }

    @GetMapping("perm3")
    public Result perm3() {
        return Result.success();
    }
}