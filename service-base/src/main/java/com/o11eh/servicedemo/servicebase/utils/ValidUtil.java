package com.o11eh.servicedemo.servicebase.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.o11eh.servicedemo.base.exception.BusinessException;
import org.springframework.validation.BindingResult;

public class ValidUtil {
    public static void checkParam(BindingResult result) {
        if (ObjectUtil.isNotNull(result) && result.hasErrors()) {
            StringBuffer buffer = new StringBuffer();
            result.getFieldErrors().forEach(error -> {
                buffer.append(error.getField());
                buffer.append(error.getDefaultMessage());
                buffer.append(StrUtil.C_COMMA);
            });
            throw BusinessException.e(buffer.substring(0, buffer.length() - 1));
        }
    }
}
