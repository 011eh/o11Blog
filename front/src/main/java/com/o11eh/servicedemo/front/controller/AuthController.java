package com.o11eh.servicedemo.front.controller;

import com.o11eh.servicedemo.front.service.AuthService;
import com.o11eh.servicedemo.servicebase.entry.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "登录注册")
@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @ApiOperation("注册")
    @PostMapping("/register")
    public Result register(String nickName, String email, String password) {
        authService.register(nickName, email, password);
        return Result.success();
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(String email, String password) {
        String token = authService.login(email, password);
        return Result.success(token);
    }
}
