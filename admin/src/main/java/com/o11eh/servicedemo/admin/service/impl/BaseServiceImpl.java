package com.o11eh.servicedemo.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.o11eh.servicedemo.admin.entry.BaseEntry;
import com.o11eh.servicedemo.admin.mapper.BaseMapperO;
import com.o11eh.servicedemo.admin.service.BaseService;

import java.util.List;

public class BaseServiceImpl<M extends BaseMapperO<T>, T extends BaseEntry> extends ServiceImpl<M, T> implements BaseService<T> {

    public static final String LIMIT_1 = "limit 1";

    public Page<T> page(long current, long size) {
        Page<T> page = new Page<>(current, size);
        return this.page(page);
    }

    public Page<T> page(long current, long size, Wrapper<T> wrapper) {
        Page<T> page = new Page<>(current, size);
        return this.page(page, wrapper);
    }

    public List<T> getDtoList(Wrapper<T> wrapper) {
        return this.list(wrapper);
    }
}
