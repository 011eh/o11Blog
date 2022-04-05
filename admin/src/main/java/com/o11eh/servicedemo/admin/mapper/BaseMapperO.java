package com.o11eh.servicedemo.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.o11eh.servicedemo.admin.entry.BaseEntry;

public interface BaseMapperO<T extends BaseEntry> extends BaseMapper<T> {
}
