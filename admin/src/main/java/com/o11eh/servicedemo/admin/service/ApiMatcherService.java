package com.o11eh.servicedemo.admin.service;

import com.o11eh.servicedemo.admin.entity.ApiMatcher;

import java.util.List;

public interface ApiMatcherService extends BaseService<ApiMatcher> {
    List<ApiMatcher> matcherList();
}
