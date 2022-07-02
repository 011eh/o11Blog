package com.o11eh.o11blog.admin.service;

import com.o11eh.o11blog.admin.entity.ApiMatcher;

import java.util.List;

public interface ApiMatcherService extends BaseService<ApiMatcher> {
    List<ApiMatcher> matcherList();
}
