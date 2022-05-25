package com.o11eh.servicedemo.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.entry.PageReq;
import com.o11eh.servicedemo.admin.entry.SysParam;

public interface SysParamService extends BaseService<SysParam> {
    String ADMIN_DEFAULT_PASSWORD = "adminDefaultPassword";

    String getValueByKey(String s);

    Page<SysParam> getPage(PageReq req);
}
