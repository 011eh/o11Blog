package com.o11eh.o11blog.admin.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.o11eh.o11blog.servicebase.entity.Result;
import com.o11eh.o11blog.admin.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@Api(tags = "登录")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(@RequestParam String username, @RequestParam String password) {
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

