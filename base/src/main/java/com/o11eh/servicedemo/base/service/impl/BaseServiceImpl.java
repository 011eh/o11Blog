package com.o11eh.servicedemo.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.o11eh.servicedemo.base.mapper.BaseMapperO;
import com.o11eh.servicedemo.base.service.BaseService;

public class BaseServiceImpl<M extends BaseMapperO<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {
    public Page<T> page(Long current, Long size) {
        Page<T> page = new Page<>(current, size);
        return this.page(page);
    }

    public LambdaQueryWrapper<T> wrapper() {
        return Wrappers.lambdaQuery();
    }
}
