package com.o11eh.o11blog.admin.service.impl;

import com.o11eh.o11blog.admin.entity.ApiMatcher;
import com.o11eh.o11blog.admin.entity.BaseEntry;
import com.o11eh.o11blog.admin.entity.Permission;
import com.o11eh.o11blog.admin.mapper.ApiMatcherMapper;
import com.o11eh.o11blog.admin.service.ApiMatcherService;
import com.o11eh.o11blog.admin.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
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
