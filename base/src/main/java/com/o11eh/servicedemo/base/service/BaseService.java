package com.o11eh.servicedemo.base.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BaseService<T> extends IService<T> {
    Page<T> page(Long current, Long size);

    LambdaQueryWrapper<T> wrapper();
}
