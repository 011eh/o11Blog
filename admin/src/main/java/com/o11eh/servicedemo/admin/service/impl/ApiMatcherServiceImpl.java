package com.o11eh.servicedemo.admin.service.impl;

import com.o11eh.servicedemo.admin.entry.ApiMatcher;
import com.o11eh.servicedemo.admin.mapper.ApiMatcherMapper;
import com.o11eh.servicedemo.admin.service.ApiMatcherService;
import org.springframework.stereotype.Service;

@Service
public class ApiMatcherServiceImpl extends BaseServiceImpl<ApiMatcherMapper, ApiMatcher> implements ApiMatcherService {
}
