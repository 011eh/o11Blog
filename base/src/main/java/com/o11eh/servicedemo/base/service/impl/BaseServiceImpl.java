package com.o11eh.servicedemo.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.o11eh.servicedemo.base.mapper.BaseMapperO;
import com.o11eh.servicedemo.base.service.BaseService;

public class BaseServiceImpl<M extends BaseMapperO<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    public static final String LIMIT_1 = "limit 1";

    public Page<T> page(Long current, Long size) {
        Page<T> page = new Page<>(current, size);
        return this.page(page);
    }

    public Page<T> page(Long current, Long size, Wrapper<T> wrapper) {
        Page<T> page = new Page<>(current, size);
        return this.page(page, wrapper);
    }
}
