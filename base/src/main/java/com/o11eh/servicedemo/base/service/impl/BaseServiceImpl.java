package com.o11eh.servicedemo.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.o11eh.servicedemo.base.mapper.BaseMapperO;
import com.o11eh.servicedemo.base.service.BaseService;

public class BaseServiceImpl<M extends BaseMapperO<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {
}
