package com.o11eh.servicedemo.admin.controller;

import com.o11eh.servicedemo.admin.entry.PageParam;
import com.o11eh.servicedemo.admin.entry.Result;

import java.util.List;

public class BaseController {
    public Result list(PageParam param) {
        throw new UnsupportedOperationException();
    }

    public Result deleteBatch(List<Long> ids) {
        throw new UnsupportedOperationException();
    }

    public Result detail(String id) {
        throw new UnsupportedOperationException();
    }
}
