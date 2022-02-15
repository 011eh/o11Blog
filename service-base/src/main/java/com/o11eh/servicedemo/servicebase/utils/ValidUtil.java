package com.o11eh.servicedemo.servicebase.utils;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.o11eh.servicedemo.servicebase.exception.BusinessException;
import org.springframework.validation.BindingResult;

public class ValidUtil {
    public static void checkParam(BindingResult result) {
        if (ObjectUtil.isNotNull(result) && result.hasErrors()) {
            StringBuffer buffer = new StringBuffer();
            result.getFieldErrors().forEach(error -> {
                buffer.append(error.getField());
                buffer.append(error.getDefaultMessage());
                buffer.append(StringPool.COMMA);
            });
            throw BusinessException.e(buffer.substring(0, buffer.length() - 1));
        }
    }
}
