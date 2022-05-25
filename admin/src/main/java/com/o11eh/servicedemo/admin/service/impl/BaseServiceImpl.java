package com.o11eh.servicedemo.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.o11eh.servicedemo.admin.mapper.BaseMapperO;
import com.o11eh.servicedemo.admin.service.BaseService;

import java.util.List;

public class BaseServiceImpl<M extends BaseMapperO<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {
    public Page<T> page(long current, long size, Wrapper<T> wrapper) {
        return this.page(new Page<>(current, size), wrapper);
    }

    public List<T> getDtoList(Wrapper<T> wrapper) {
        return this.list(wrapper);
    }
}
