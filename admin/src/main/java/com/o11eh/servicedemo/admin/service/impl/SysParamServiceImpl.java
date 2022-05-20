package com.o11eh.servicedemo.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.o11eh.servicedemo.admin.config.BusinessException;
import com.o11eh.servicedemo.admin.entry.SysParam;
import com.o11eh.servicedemo.admin.mapper.SysParamMapper;
import com.o11eh.servicedemo.admin.service.SysParamService;
import org.springframework.stereotype.Service;

@Service
public class SysParamServiceImpl extends BaseServiceImpl<SysParamMapper, SysParam> implements SysParamService {
    @Override
    public String getValueByKey(String key) {
        return getByKey(key).getValue();
    }

    private SysParam getByKey(String key) {
        SysParam param = this.getOne(Wrappers.<SysParam>lambdaQuery()
                .select(SysParam::getValue).eq(SysParam::getParamKey, key));
        if (param == null) {
            throw BusinessException.e(StrUtil.format("没有配置{}参数", key));
        }
        return param;
    }
}
