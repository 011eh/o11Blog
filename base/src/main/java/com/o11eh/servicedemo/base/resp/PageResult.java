package com.o11eh.servicedemo.base.resp;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.base.constants.ResultCode;
import com.o11eh.servicedemo.base.constants.ResultMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResult<T> extends Result {
    private Long pageSize;
    private Long pageCurrent;
    private List<T> data;

    public PageResult(Page<T> page) {
        super(true, ResultCode.SUCCESS, ResultMessage.SUCCESS);
        this.pageCurrent = page.getCurrent();
        this.pageSize = page.getSize();
        this.data = page.getRecords();
    }
}
