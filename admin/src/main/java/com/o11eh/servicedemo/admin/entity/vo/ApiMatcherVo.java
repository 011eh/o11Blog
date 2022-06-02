package com.o11eh.servicedemo.admin.entity.vo;

import com.o11eh.servicedemo.admin.config.validation.RefId;
import lombok.Data;

import java.util.List;

@Data
public class ApiMatcherVo {
    private List<String> matches;
    private List<String> methods;
    private List<String> noMatches;

    @RefId("back_permission")
    private List<String> requiredAny;

    @RefId("back_permission")
    private List<String> requiredAll;
    private boolean stopChain;
    private Integer sort;
}
