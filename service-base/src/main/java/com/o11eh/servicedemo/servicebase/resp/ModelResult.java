package com.o11eh.servicedemo.servicebase.resp;

import com.o11eh.servicedemo.servicebase.enums.ResultMessage;
import lombok.Getter;
import lombok.Setter;

@Setter
public class ModelResult<T> extends Result {
    private T data;

    public ModelResult(T data) {
        super(true, ResultMessage.SUCCESS);
        this.data = data;
    }
}
