package com.o11eh.servicedemo.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.o11eh.servicedemo.admin.entry.BaseEntry;

public interface BaseService<T extends BaseEntry> extends IService<T> {
    default Page<T> page(long current, long size) {
        throw new UnsupportedOperationException();
    }
}
