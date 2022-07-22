package com.o11eh.o11blog.servicebase.entity;

import com.o11eh.o11blog.servicebase.constants.ResultCode;
import com.o11eh.o11blog.servicebase.constants.ResultMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 011eh
 * @since 2022/02/14 19:11
 */
@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
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

    public static <T> Result pageResult(long pageCurrent, long pageSize, long total, List<T> data) {
        return new PageResult<>(pageCurrent, pageSize, total, data);
    }

    public static Result error(String message) {
        return new Result(false, ResultCode.ERROR, message);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ModelResult<T> extends Result {
        private T data;

        public ModelResult(T data) {
            super(true, ResultCode.SUCCESS, ResultMessage.SUCCESS);
            this.data = data;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PageResult<T> extends Result {
        private Long pageSize;
        private Long pageCurrent;
        private Long total;
        private List<T> data;

        public PageResult(long pageCurrent, long pageSize, long total, List<T> data) {
            super(true, ResultCode.SUCCESS, ResultMessage.SUCCESS);
            this.pageCurrent = pageCurrent;
            this.pageSize = pageSize;
            this.total = total;
            this.data = data;
        }
    }
}


