package com.o11eh.servicedemo.base.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BaseService<T> extends IService<T> {
    default Page<T> page(Long current, Long size) {
        throw new UnsupportedOperationException();
    }
}
