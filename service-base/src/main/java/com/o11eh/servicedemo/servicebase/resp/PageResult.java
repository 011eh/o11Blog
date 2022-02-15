package com.o11eh.servicedemo.servicebase.resp;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.servicebase.enums.ResultMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
public class PageResult<T> extends Result {
    private Long pageSize;
    private Long pageCurrent;
    private List<T> data;

    public PageResult(Page<T> page) {
        super(true, ResultMessage.SUCCESS);
        this.pageCurrent = page.getCurrent();
        this.pageSize = page.getSize();
        this.data = page.getRecords();
    }
}
