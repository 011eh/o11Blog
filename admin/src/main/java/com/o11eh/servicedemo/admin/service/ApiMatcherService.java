package com.o11eh.servicedemo.admin.service;

import com.o11eh.servicedemo.admin.entry.ApiMatcher;

import java.util.Set;

public interface ApiMatcherService extends BaseService<ApiMatcher> {
    Set<String> getAllUrl();
}
