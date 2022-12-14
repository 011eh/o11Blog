package com.o11eh.o11blog.admin.entity;

import com.o11eh.o11blog.servicebase.entity.BaseEntry;
import lombok.Data;

@Data
public class SysParam extends BaseEntry {

    private String name;
    private String paramKey;
    private String value;
    private Integer sort;
    private String remark;

}
