package com.o11eh.servicedemo.admin.config;

import cn.dev33.satoken.exception.NotPermissionException;
import com.o11eh.servicedemo.admin.entry.Result;
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
        return Result.error(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(NotPermissionException.class)
    public Result handle2(NotPermissionException e) {
        e.printStackTrace();
        return Result.error("无权限进行访问").code(401);
    }
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handle3(MethodArgumentNotValidException e) {
        e.printStackTrace();
        return Result.error(e.getMessage()).code(1402);
    }
}
