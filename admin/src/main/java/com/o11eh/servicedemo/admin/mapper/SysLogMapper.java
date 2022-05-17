package com.o11eh.servicedemo.admin.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.entry.SysLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysLogMapper extends BaseMapperO<SysLog> {
    Page<SysLog> selectPage(Page<SysLog> page);
}
