package com.o11eh.servicedemo.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.o11eh.servicedemo.admin.entry.SysLog;
import com.o11eh.servicedemo.admin.mapper.SysLogMapper;
import com.o11eh.servicedemo.admin.service.SysLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Async
    @Override
    public void saveLog(SysLog sysLog) {
        this.save(sysLog);
    }
}
