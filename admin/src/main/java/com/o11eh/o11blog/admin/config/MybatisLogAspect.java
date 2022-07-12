package com.o11eh.o11blog.admin.config;

import cn.dev33.satoken.stp.StpUtil;
import com.o11eh.o11blog.admin.service.SysLogService;
import com.o11eh.o11blog.servicebase.config.log.AbstractLogAspect;
import com.o11eh.o11blog.servicebase.config.log.Log;
import com.o11eh.o11blog.servicebase.entity.SysLog;
import com.o11eh.o11blog.servicebase.enums.LogStatus;
import com.o11eh.o11blog.servicebase.utils.HttpUtil;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Aspect
@Component
@AllArgsConstructor
public class MybatisLogAspect extends AbstractLogAspect {

    private SysLogService sysLogService;

    public void doSaveLog(String params, Log annotation, ProceedingJoinPoint joinPoint, int timeCost, Exception e) {
        HttpServletRequest request = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        String username = (String) StpUtil.getLoginIdDefaultNull();
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

        sysLogService.saveLog(new SysLog(username, ip, operation, controller, method, uri, httpMethod, params, status,
                timeCost, exception));

    }
}
