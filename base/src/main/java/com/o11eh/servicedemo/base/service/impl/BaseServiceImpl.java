package com.o11eh.servicedemo.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.o11eh.servicedemo.base.mapper.BaseMapper0;
import com.o11eh.servicedemo.base.service.BaseService;

public class BaseServiceImpl<M extends BaseMapper0<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {
}
