package com.o11eh.servicedemo.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.o11eh.servicedemo.base.exception.BusinessException;
import com.o11eh.servicedemo.base.resp.Result;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class LoginController {
    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(@RequestParam String username, @RequestParam String password) {
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

    @GetMapping("toLogin")
    public Result toLogin() {
        throw BusinessException.e("您尚未登录");
    }

    @GetMapping("unauthorized")
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
