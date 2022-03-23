package com.o11eh.servicedemo.admin.config;

import com.o11eh.servicedemo.admin.entry.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(Exception e) {
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(DisabledAccountException.class)
    @ResponseBody
    public Result handle1() {
        return Result.error("该账号已被锁定");
    }

    @ExceptionHandler({IncorrectCredentialsException.class, UnknownAccountException.class})
    @ResponseBody
    public Result handle3() {
        return Result.error("帐号或密码错误");
    }

}
