package com.o11eh.servicedemo.admin.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.entry.SysLog;
import com.o11eh.servicedemo.admin.entry.vo.SysLogPageReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysLogMapper extends BaseMapperO<SysLog> {
}
