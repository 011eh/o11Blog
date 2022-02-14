package com.o11eh.servicedemo.servicebase.exceptionhandler;

import com.o11eh.servicedemo.servicebase.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<?> handleException(Exception e) {
        log.error(e.getMessage());
        return Result.error();
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result<?> handleBusinessException(BusinessException e) {
        log.error(e.getMessage());
        return Result.error();
    }
}
