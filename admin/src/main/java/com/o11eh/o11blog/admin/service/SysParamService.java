package com.o11eh.o11blog.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.o11blog.servicebase.entity.PageReq;
import com.o11eh.o11blog.admin.entity.SysParam;

public interface SysParamService extends BaseService<SysParam> {
    String ADMIN_DEFAULT_PASSWORD = "adminDefaultPassword";

    String getValueByKey(String s);

    Page<SysParam> getPage(PageReq req);
}
