package com.o11eh.servicedemo.admin.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.o11eh.servicedemo.admin.entry.Result;
import com.o11eh.servicedemo.admin.service.AdminService;
import com.o11eh.servicedemo.admin.service.AuthService;
import com.o11eh.servicedemo.admin.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
@Api(tags = "登录")
public class AuthController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private AuthService authService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(@Valid @RequestParam String username, @Valid @RequestParam String password, boolean rememberMe) {
        String token = authService.login(username, password);
        return Result.success(token);
    }


    @ApiOperation("用户信息")
    @GetMapping("info")
    public Result getUserInfo() {
        Object authInfo = StpUtil.getSession().get("authInfo");
        return Result.success(authInfo);
    }

    @ApiOperation("注销")
    @GetMapping("logout")
    public Result logout() {
        StpUtil.logout();
        return Result.success();
    }
}

