package com.o11eh.servicedemo.base.resp;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.base.constants.ResultMessage;
import com.o11eh.servicedemo.base.constants.ResultCode;
import lombok.Getter;
import lombok.Setter;

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

    public static Result error() {
        return new Result(false, ResultCode.ERROR, ResultMessage.ERROR);
    }

    public static Result error(String message) {
        return new Result(false, ResultCode.ERROR, message);
    }
}


