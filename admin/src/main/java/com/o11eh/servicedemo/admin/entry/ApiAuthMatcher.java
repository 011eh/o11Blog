package com.o11eh.servicedemo.admin.entry;

import lombok.Data;

import java.util.List;

@Data
public class ApiAuthMatcher extends BaseEntry{

    private List<String> apis;
    private List<String> method;
    private List<String> noMatches;
    private List<String> requiredAny;
    private List<String> requiredAll;
    private boolean stopChain;
    private Integer sort;

}
