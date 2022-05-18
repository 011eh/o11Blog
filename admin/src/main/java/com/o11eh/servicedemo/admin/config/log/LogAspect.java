package com.o11eh.servicedemo.admin.config.log;

import cn.dev33.satoken.stp.StpUtil;
import com.o11eh.servicedemo.admin.entry.SysLog;
import com.o11eh.servicedemo.admin.enums.LogStatus;
import com.o11eh.servicedemo.admin.service.SysLogService;
import com.o11eh.servicedemo.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
    private SysLogService sysLogService;

    @Around(value = "@annotation(logAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint, Log logAnnotation) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result;
        Exception e = null;
        try {
            result = joinPoint.proceed();
        } catch (Exception ex) {
            e = ex;
            throw e;
        } finally {
            int timeCost = Math.toIntExact(System.currentTimeMillis() - startTime);
            doSaveLog(logAnnotation, joinPoint, timeCost, e);
        }
        return result;
    }

    @Async
    public void doSaveLog(Log annotation, ProceedingJoinPoint joinPoint, int timeCost, Exception e) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        String userId = (String) StpUtil.getLoginIdDefaultNull();
        String ip = HttpUtil.getIPAddress(request);
        String operation = annotation.value().length() > 0 ? annotation.value() : null;
        String controller = joinPoint.getTarget().getClass().getName();
        String method = joinPoint.getSignature().getName();
        String uri = request.getRequestURI();
        String httpMethod = request.getMethod();

        LogStatus status = LogStatus.SUCCESS;
        String exception = null;
        if (e != null) {
            exception = e.getMessage();
            status = LogStatus.FAIL;
        }

        Object params = null;
        Object[] args = joinPoint.getArgs();
        if (args.length == 1) {
            params = args[0];
        } else if (args.length > 1) {
            params = args;
        }
        sysLogService.saveLog(new SysLog(userId, ip, operation, controller, method, uri, httpMethod, params, status,
                timeCost, exception));

    }
}
