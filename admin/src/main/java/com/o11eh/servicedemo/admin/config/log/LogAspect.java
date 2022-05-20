package com.o11eh.servicedemo.admin.config.log;

import cn.dev33.satoken.stp.StpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private SysLogService sysLogService;

    private static final ObjectMapper MAPPER;

    static {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        MAPPER = new ObjectMapper();
        MAPPER.registerModule(javaTimeModule);
    }

    @Around(value = "@annotation(logAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint, Log logAnnotation) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result;
        Exception e = null;
        String params = null;
        Object[] args = joinPoint.getArgs();
        if (args.length == 1) {
            params = MAPPER.writeValueAsString(args[0]);
        } else if (args.length > 1) {
            params = MAPPER.writeValueAsString(args);
        }
        try {
            result = joinPoint.proceed();
        } catch (Exception ex) {
            e = ex;
            throw e;
        } finally {
            int timeCost = Math.toIntExact(System.currentTimeMillis() - startTime);
            doSaveLog(params, logAnnotation, joinPoint, timeCost, e);
        }
        return result;
    }

    @Async
    public void doSaveLog(String params, Log annotation, ProceedingJoinPoint joinPoint, int timeCost, Exception e) {
        HttpServletRequest request = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        String username = (String) StpUtil.getSession().get("username");
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
