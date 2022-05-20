package com.o11eh.servicedemo.admin.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class SysParam extends BaseEntry {

    private String name;

    private String paramKey;
    private String value;
    private Integer sort;
    private String remark;

    public int getIntValue() {
        return Integer.parseInt(value);
    }
}
