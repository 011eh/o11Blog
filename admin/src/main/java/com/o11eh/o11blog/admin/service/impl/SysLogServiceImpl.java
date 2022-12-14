package com.o11eh.o11blog.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.o11blog.admin.entity.vo.SysLogPageReq;
import com.o11eh.o11blog.admin.mapper.SysLogMapper;
import com.o11eh.o11blog.admin.service.SysLogService;
import com.o11eh.o11blog.servicebase.entity.SysLog;
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
                .ge(req.getStartTime() != null, SysLog::getCreateTime, req.getStartTime())
                .le(req.getStartTime() != null, SysLog::getCreateTime, req.getEndTime());

        Page<SysLog> page = sysLogMapper.selectPage(new Page<>(req.getCurrent(), req.getSize()), wrapper);
        return page;
    }
}
