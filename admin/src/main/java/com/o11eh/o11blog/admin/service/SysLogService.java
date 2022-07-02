package com.o11eh.o11blog.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.o11blog.admin.entity.SysLog;
import com.o11eh.o11blog.admin.entity.vo.SysLogPageReq;

public interface SysLogService extends BaseService<SysLog> {

    void saveLog(SysLog sysLog);

    Page<SysLog> page(SysLogPageReq req);
}
