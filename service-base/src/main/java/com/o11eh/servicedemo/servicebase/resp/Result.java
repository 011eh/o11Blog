package com.o11eh.servicedemo.servicebase.resp;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.servicebase.constants.ResultCode;
import com.o11eh.servicedemo.servicebase.enums.ResultMessage;
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

    Result(boolean success, ResultMessage message) {
        this.success = success;
        this.code = message.getCode();
        this.msg = message.getMsg();
    }

    public static Result success() {
        return new Result(true, ResultMessage.SUCCESS);
    }

    public static <T> Result success(T Data) {
        return new ModelResult<>(Data);
    }

    public static <T> Result success(Page<T> page) {
        return new PageResult<>(page);
    }

    public static Result error() {
        return new Result(false, ResultMessage.ERROR);
    }

    public static Result error(String message) {
        return new Result(false, ResultCode.ERROR, message);
    }
}


