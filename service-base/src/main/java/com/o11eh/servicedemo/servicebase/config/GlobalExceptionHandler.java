package com.o11eh.servicedemo.servicebase.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.o11eh.servicedemo.servicebase.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result handle1(Exception e) {
        e.printStackTrace();
        return Result.error(e.getMessage()).code(1500);
    }

    @ResponseBody
    @ExceptionHandler(NotPermissionException.class)
    public Result handle2(NotPermissionException e) {
        log.error(e.getMessage());
        return Result.error("无权限进行访问").code(1401);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handle3(MethodArgumentNotValidException e) {
        e.printStackTrace();
        return Result.error(e.getMessage()).code(1402);
    }

    @ResponseBody
    @ExceptionHandler(NotLoginException.class)
    public Result handle3(NotLoginException e) {
        log.info(e.getMessage());
        e.printStackTrace();
        return Result.error(e.getMessage()).code(1403);
    }
}
