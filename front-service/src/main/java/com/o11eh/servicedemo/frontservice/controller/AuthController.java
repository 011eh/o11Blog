package com.o11eh.servicedemo.frontservice.controller;

import com.o11eh.servicedemo.frontservice.entity.Member;
import com.o11eh.servicedemo.frontservice.service.MemberService;
import com.o11eh.servicedemo.servicebase.entry.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "登录注册")
@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {

    private MemberService memberService;

    @ApiOperation("注册")
    @PostMapping("/register")
    public Result register(@Valid @RequestBody Member member) {
        memberService.register(member);
        return Result.success();
    }
}
