package com.o11eh.servicedemo.admin.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StatusEnum {
    Disable(0),
    Enable(1);
    private final Integer status;

    public int getStatus() {
        return status;
    }
}
