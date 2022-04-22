package com.o11eh.servicedemo.admin.config;

import cn.dev33.satoken.exception.NotPermissionException;
import com.o11eh.servicedemo.admin.entry.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handle1(Exception e) {
        e.printStackTrace();
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(NotPermissionException.class)
    @ResponseBody
    public Result handle2(NotPermissionException e) {
        e.printStackTrace();
        return Result.error("无权限进行访问");
    }
}
