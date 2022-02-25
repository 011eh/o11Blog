package com.o11eh.servicedemo.admin.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.server.HttpServerRequest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.constants.ApiConstants;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.security.ShiroUtils;
import com.o11eh.servicedemo.admin.service.AdminService;
import com.o11eh.servicedemo.base.constants.BaseApiConstants;
import com.o11eh.servicedemo.base.controller.BaseController;
import com.o11eh.servicedemo.base.exception.BusinessException;
import com.o11eh.servicedemo.base.req.PageParams;
import com.o11eh.servicedemo.base.resp.Result;
import com.o11eh.servicedemo.base.validation.groups.Add;
import com.o11eh.servicedemo.base.validation.groups.Update;
import com.o11eh.servicedemo.servicebase.utils.ValidUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
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

public class AdminController extends BaseController {

    @Autowired
    private AdminService adminService;

    @PostMapping(BaseApiConstants.ADD)
    @ApiOperation("增加管理员")
    @RequiresRoles(ShiroUtils.SUPER_ADMIN)
    public Result add(@Validated(Add.class) @RequestBody Admin admin, BindingResult result) {
        ValidUtil.checkParam(result);
        Long id = adminService.add(admin);
        return Result.success(id);
    }

    @ApiOperation("管理员分页查询")
    @PostMapping(BaseApiConstants.PAGE)
    @RequiresRoles(ShiroUtils.SUPER_ADMIN)
    public Result page(@Validated PageParams params, BindingResult result) {
        ValidUtil.checkParam(result);
        Page<Admin> page = adminService.getPage(params.getCurrent(), params.getSize());
        return Result.success(page);
    }

    @ApiOperation("管理员更新")
    @PutMapping(BaseApiConstants.UPDATE)
    @RequiresRoles(ShiroUtils.SUPER_ADMIN)
    public Result update(@Validated(Update.class) @RequestBody Admin admin, BindingResult result) {
        ValidUtil.checkParam(result);
        Long id = adminService.updateAdmin(admin);
        return Result.success(id);
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

    @GetMapping("/noLogin")
    public void noLogin() {
        throw BusinessException.e("请登录");
    }

    @GetMapping("authc")
    public Result authc() {
        return Result.success();
    }

    @GetMapping("anon")
    public Result anon() {
        return Result.success();
    }

    @GetMapping("admin")
    public Result admin() {
        return Result.success();
    }

    @GetMapping("normal")
    public Result normal() {
        return Result.success();
    }

    @GetMapping("/unauthorized")
    public Result unauthorized() {
        throw BusinessException.e("无相关权限进行操作");
    }

    @GetMapping("/toLogin")
    public Result toLogin() {
        throw BusinessException.e("您尚未登录");
    }
}