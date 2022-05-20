package com.o11eh.servicedemo.admin.service;

import com.o11eh.servicedemo.admin.entry.SysParam;

public interface SysParamService extends BaseService<SysParam> {
    String ADMIN_DEFAULT_PASSWORD = "adminDefaultPassword";

    String getValueByKey(String s);
}
