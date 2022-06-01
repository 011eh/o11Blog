package com.o11eh.servicedemo.servicebase.entry;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.servicebase.constants.ResultCode;
import com.o11eh.servicedemo.servicebase.constants.ResultMessage;
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

    public static <T> Result successShowMsg() {
        Result result = new Result(true, ResultCode.SUCCESS, ResultMessage.SUCCESS);
        result.code += 1000;
        return result;
    }

    public static <T> Result successShowMsg(T data) {
        Result result = new ModelResult<T>(data);
        result.code += 1000;
        return result;
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

    public Result msg(String msg) {
        this.msg = msg;
        return this;
    }

    public Result code(int code) {
        this.code = code;
        return this;
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
        private Long total;
        private List<T> data;

        public PageResult(Page<T> page) {
            super(true, ResultCode.SUCCESS, ResultMessage.SUCCESS);
            this.pageCurrent = page.getCurrent();
            this.pageSize = page.getSize();
            this.data = page.getRecords();
            total = page.getTotal();
        }
    }
}


