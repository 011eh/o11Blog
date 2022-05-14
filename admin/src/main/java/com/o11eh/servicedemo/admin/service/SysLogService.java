package com.o11eh.servicedemo.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.o11eh.servicedemo.admin.entry.SysLog;

public interface SysLogService extends IService<SysLog> {

    void saveLog(SysLog sysLog);
}
