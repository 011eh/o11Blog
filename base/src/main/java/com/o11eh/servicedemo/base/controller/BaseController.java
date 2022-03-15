package com.o11eh.servicedemo.base.controller;

import com.o11eh.servicedemo.base.req.PageParam;
import com.o11eh.servicedemo.base.resp.Result;
import org.springframework.validation.BindingResult;

import java.util.List;

public class BaseController {
    public Result page(PageParam param) {
        throw new UnsupportedOperationException();
    }

    public Result deleteBatch(List<Long> ids) {
        throw new UnsupportedOperationException();
    }

    public Result detail(Long id) {
        throw new UnsupportedOperationException();
    }
}
