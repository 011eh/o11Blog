package com.o11eh.servicedemo.admin.config.log;

import cn.dev33.satoken.stp.StpUtil;
import com.o11eh.servicedemo.admin.entry.SysLog;
import com.o11eh.servicedemo.admin.mapper.SysLogMapper;
import com.o11eh.servicedemo.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Pointcut("@annotation(com.o11eh.servicedemo.admin.config.log.Log)")
    public void around() {
    }

    @Around(value = "around()&&@annotation(logAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint, Log logAnnotation) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result;

        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            throw e;
        }

        long timeCost = System.currentTimeMillis() - startTime;
        doSaveLog(logAnnotation,joinPoint, timeCost);
        return result;
    }

    public void doSaveLog(Log annotation, ProceedingJoinPoint joinPoint, long timeCost) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        String ip = HttpUtil.getIPAddress(request);
        String operation = annotation.operation();
        String controller = joinPoint.getTarget().getClass().toString();
        String contextPath = request.getContextPath();
    }
}
