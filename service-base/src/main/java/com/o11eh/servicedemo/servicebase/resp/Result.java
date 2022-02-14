package com.o11eh.servicedemo.servicebase.resp;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.servicebase.enums.ResultMessage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author 011eh
 * @since 2022/02/14 19:11
 */
@AllArgsConstructor
@Data
public class Result<T> {
    private boolean success;
    private int code;
    private String msg;
    private T data;

    private Result(ResultMessage message) {
        this.code = message.getCode();
        this.msg = message.getMsg();
    }

    public Result(ResultMessage resultMessage, T data) {
        this(resultMessage);
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(ResultMessage.SUCCESS, data);
        result.success = true;
        return result;
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> error(ResultMessage message) {
        return new Result<>(message);
    }

    public static <T> Result<T> error() {
        return error(ResultMessage.ERROR);
    }

    public static <E> Result<PageResult<E>> page(Page<E> page) {
        return new Result<>(ResultMessage.SUCCESS, new PageResult<>(page));
    }

    @Data
    static class PageResult<E> {
        private final long pageSize;
        private final long pageCurrent;
        private final List<E> data;

        public PageResult(Page<E> page) {
            this.pageSize = page.getSize();
            this.pageCurrent = page.getCurrent();
            this.data = page.getRecords();
        }
    }
}



