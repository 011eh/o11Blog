package com.o11eh.servicedemo.admin.service;

import com.o11eh.servicedemo.admin.entry.ApiMatcher;

import java.util.List;
import java.util.Set;

public interface ApiMatcherService extends BaseService<ApiMatcher> {
    List<ApiMatcher> matcherList();
}
