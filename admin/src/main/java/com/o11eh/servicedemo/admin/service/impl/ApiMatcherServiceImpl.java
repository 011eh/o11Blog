package com.o11eh.servicedemo.admin.service.impl;

import com.o11eh.servicedemo.admin.entry.ApiMatcher;
import com.o11eh.servicedemo.admin.mapper.ApiMatcherMapper;
import com.o11eh.servicedemo.admin.service.ApiMatcherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ApiMatcherServiceImpl extends BaseServiceImpl<ApiMatcherMapper, ApiMatcher> implements ApiMatcherService {

    private RequestMappingHandlerMapping handlerMapping;

    @Override
    public Set<String> getAllUrl() {
        Set<String> urls = handlerMapping.getHandlerMethods().entrySet().stream()
                .filter(entry -> Pattern.matches("com\\.o11eh\\..*", entry.getValue().toString()))
                .map(entry -> entry.getKey().getPatternsCondition().getPatterns())
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(s -> s)).collect(Collectors.toCollection(LinkedHashSet::new));
        return urls;
    }
}
