package com.o11eh.servicedemo.base.controller;

import com.o11eh.servicedemo.base.req.PageParam;
import com.o11eh.servicedemo.base.resp.Result;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class BaseController {
    //@PostMapping(BaseApiConstants.PAGE)
    public Result page(PageParam param, BindingResult result) {
        throw new UnsupportedOperationException();
    }

    //@DeleteMapping(BaseApiConstants.DELETE)
    public Result deleteBatch(List<Long> ids) {
        throw new UnsupportedOperationException();
    }

    //@GetMapping(BaseApiConstants.PATH_ID)
    public Result detail(@PathVariable Long id) {
        throw new UnsupportedOperationException();
    }
}
