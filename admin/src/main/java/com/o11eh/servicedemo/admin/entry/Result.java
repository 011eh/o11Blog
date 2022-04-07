package com.o11eh.servicedemo.admin.entry;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.constants.ResultCode;
import com.o11eh.servicedemo.admin.constants.ResultMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 011eh
 * @since 2022/02/14 19:11
 */
@Setter
@Getter
public class Result {
    private boolean success;
    private int code;
    private String msg;

    Result(boolean success, int code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    public static Result success() {
        return new Result(true, ResultCode.SUCCESS, ResultMessage.SUCCESS);
    }

    public static <T> Result success(T Data) {
        return new ModelResult<>(Data);
    }

    public static <T> Result success(Page<T> page) {
        return new PageResult<>(page);
    }

    public static Result error(String message) {
        return new Result(false, ResultCode.ERROR, message);
    }

    @Getter
    @Setter
    public static class ModelResult<T> extends Result {
        private T data;

        public ModelResult(T data) {
            super(true, ResultCode.SUCCESS, ResultMessage.SUCCESS);
            this.data = data;
        }
    }

    @Getter
    @Setter
    public static class PageResult<T> extends Result {
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
}

