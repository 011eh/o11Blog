package com.o11eh.o11blog.member.controller;

import com.o11eh.o11blog.member.repository.MemberService;
import com.o11eh.o11blog.servicebase.config.log.Log;
import com.o11eh.o11blog.servicebase.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "登录注册")
@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class MemberController {

    private MemberService memberService;

    @Log
    @ApiOperation("注册")
    @PostMapping("/register")
    public Result register(String email, String password) {
        memberService.register(email, password);
        return Result.success();
    }

    @Log
    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(String email, String password) {
        String token = memberService.login(email, password);
        return Result.success(token);
    }

    @Log
    @ApiOperation("激活")
    @GetMapping("/activate/{token}")
    public Result activate(@PathVariable String token) {
        memberService.activate(token);
        return Result.success();
    }
}
