package com.o11eh.servicedemo.base.resp;

import com.o11eh.servicedemo.base.constants.ResultMessage;
import com.o11eh.servicedemo.base.constants.ResultCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelResult<T> extends Result {
    private T data;

    public ModelResult(T data) {
        super(true, ResultCode.SUCCESS, ResultMessage.SUCCESS);
        this.data = data;
    }
}
