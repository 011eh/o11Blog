package com.o11eh.servicedemo.admin.config;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.router.SaRouterStaff;
import cn.dev33.satoken.stp.StpUtil;
import com.o11eh.servicedemo.admin.entity.ApiMatcher;
import com.o11eh.servicedemo.admin.service.ApiMatcherService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@AllArgsConstructor
public class SaTokenConfig implements WebMvcConfigurer {

    private ApiMatcherService apiMatcherService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaRouteInterceptor((request, response, handler) -> {
            List<ApiMatcher> matchers = apiMatcherService.matcherList();
            for (ApiMatcher matcher : matchers) {
                SaRouterStaff check = SaRouter
                        .match(matcher.getMatches())
                        .notMatch(matcher.getNoMatches())
                        .match(SaHttpMethod.toEnumArray(matcher.getMethods().toArray(new String[0])))
                        .check(() -> StpUtil.checkPermissionAnd(matcher.getRequiredAll().toArray(new String[0])))
                        .check(() -> StpUtil.checkPermissionOr(matcher.getRequiredAny().toArray(new String[0])));
                if (matcher.isStopChain()) {
                    check.stop();
                }
            }
        }));
    }
}
