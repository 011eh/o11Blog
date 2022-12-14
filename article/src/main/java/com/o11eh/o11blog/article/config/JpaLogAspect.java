package com.o11eh.o11blog.article.config;

import com.o11eh.o11blog.servicebase.entity.SysLog;
import com.o11eh.o11blog.article.repository.SysLogRepository;
import com.o11eh.o11blog.servicebase.config.log.AbstractLogAspect;
import com.o11eh.o11blog.servicebase.config.log.Log;
import com.o11eh.o11blog.servicebase.enums.LogStatus;
import com.o11eh.o11blog.servicebase.utils.HttpUtil;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@ConditionalOnClass(JpaRepositoriesAutoConfiguration.class)
@Aspect
@Component
@AllArgsConstructor
public class JpaLogAspect extends AbstractLogAspect {

    private SysLogRepository sysLogRepository;

    @Override
    public void doSaveLog(String params, Log annotation, ProceedingJoinPoint joinPoint, int timeCost, Exception e) {
        HttpServletRequest request = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

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
        sysLogRepository.save(new SysLog(ip, operation, controller, method, uri, httpMethod, params, status, timeCost, exception));
    }
}
