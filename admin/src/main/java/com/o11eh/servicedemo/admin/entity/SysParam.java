package com.o11eh.servicedemo.admin.entity;

import lombok.Data;

@Data
public class SysParam extends BaseEntry {

    private String name;
    private String paramKey;
    private String value;
    private Integer sort;
    private String remark;

}