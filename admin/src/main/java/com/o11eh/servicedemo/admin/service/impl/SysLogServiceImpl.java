package com.o11eh.servicedemo.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.entity.DataAccess;
import com.o11eh.servicedemo.admin.entity.SysLog;
import com.o11eh.servicedemo.admin.entity.vo.SysLogPageReq;
import com.o11eh.servicedemo.admin.mapper.SysLogMapper;
import com.o11eh.servicedemo.admin.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl extends BaseServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Async
    @Override
    public void saveLog(SysLog sysLog) {
        this.save(sysLog);
    }

    @Override
    public Page<SysLog> page(SysLogPageReq req) {

        Wrapper<SysLog> wrapper = Wrappers.<SysLog>lambdaQuery()
                .in(CollUtil.isNotEmpty(req.getOperators()), SysLog::getUsername, req.getOperators())
                .like(StrUtil.isNotBlank(req.getIp()), SysLog::getIp, req.getIp())
                .like(StrUtil.isNotBlank(req.getOperation()), SysLog::getOperation, req.getOperation())
                .ge(req.getStartTime() != null, DataAccess::getCreateTime, req.getStartTime())
                .le(req.getStartTime() != null, DataAccess::getCreateTime, req.getEndTime());

        Page<SysLog> page = sysLogMapper.selectPage(new Page<>(req.getCurrent(), req.getSize()), wrapper);
        return page;
    }
}
