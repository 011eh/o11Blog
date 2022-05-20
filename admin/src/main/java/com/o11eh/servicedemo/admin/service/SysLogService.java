package com.o11eh.servicedemo.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.o11eh.servicedemo.admin.entry.SysLog;
import com.o11eh.servicedemo.admin.entry.vo.SysLogPageReq;
import com.o11eh.servicedemo.admin.mapper.SysLogMapper;
import com.o11eh.servicedemo.admin.service.impl.BaseServiceImpl;

public interface SysLogService extends BaseService<SysLog> {

    void saveLog(SysLog sysLog);

    Page<SysLog> page(SysLogPageReq req);
}
