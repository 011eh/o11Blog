package com.o11eh.servicedemo.admin.service.impl;

import com.o11eh.servicedemo.admin.mapper.DBUtilMapper;
import com.o11eh.servicedemo.admin.service.DBUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBUtilServiceImpl implements DBUtilService {

    @Autowired
    private DBUtilMapper dbUtilMapper;

    @Override
    public boolean recordExists(String table, String id) {
        return dbUtilMapper.recordExists(table, id);
    }
}
