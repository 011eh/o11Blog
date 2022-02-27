package com.o11eh.servicedemo.admin.config;

import cn.hutool.core.util.ObjectUtil;
import com.o11eh.servicedemo.base.exception.BusinessException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * @author 011eh
 * @since 2022/02/27 19:20
 */
@Aspect
@Component
public class ValidateParamAspect {

    @Pointcut("execution(* com.o11eh.servicedemo.*.controller.*.*(..,org.springframework.validation.BindingResult))")
    public void validateParams() {
    }

    @Before("validateParams()&&args(..,result)")
    public void validate(BindingResult result) {
        if (ObjectUtil.isNotNull(result) && result.hasErrors()) {
            StringBuffer buffer = new StringBuffer();
            result.getFieldErrors().forEach(error -> {
                buffer.append(error.getField());
                buffer.append(error.getDefaultMessage());
                buffer.append(", ");
            });
            throw BusinessException.e(buffer.substring(0, buffer.length() - 1));
        }
    }
}
