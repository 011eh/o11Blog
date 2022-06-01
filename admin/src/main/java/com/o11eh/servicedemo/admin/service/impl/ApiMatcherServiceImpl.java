package com.o11eh.servicedemo.admin.service.impl;

import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.router.SaRouterStaff;
import cn.dev33.satoken.stp.StpUtil;
import com.o11eh.servicedemo.admin.entry.ApiMatcher;
import com.o11eh.servicedemo.admin.entry.BaseEntry;
import com.o11eh.servicedemo.admin.entry.Permission;
import com.o11eh.servicedemo.admin.mapper.ApiMatcherMapper;
import com.o11eh.servicedemo.admin.service.ApiMatcherService;
import com.o11eh.servicedemo.admin.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ApiMatcherServiceImpl extends BaseServiceImpl<ApiMatcherMapper, ApiMatcher> implements ApiMatcherService {

    private PermissionService permissionService;

    @Override
    public List<ApiMatcher> matcherList() {
        Map<String, String> idKeyMap = permissionService.dtoList().stream()
                .collect(Collectors.toMap(BaseEntry::getId, Permission::getPermissionKey));
        List<ApiMatcher> list = this.list();
        for (ApiMatcher matcher : list) {
            List<String> requiredAll = matcher.getRequiredAll().stream().map(idKeyMap::get).collect(Collectors.toList());
            List<String> requiredAny = matcher.getRequiredAny().stream().map(idKeyMap::get).collect(Collectors.toList());
            matcher.setRequiredAll(requiredAll);
            matcher.setRequiredAny(requiredAny);
        }
        return list;
    }
}
