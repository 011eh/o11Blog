package com.o11eh.servicedemo.admin.config;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    @AllArgsConstructor
    static class InterceptRule {
        private String path;
        private SaHttpMethod method;
        private String permissionKey;

    }

    public static final List<InterceptRule> INTERCEPT_RULES = new ArrayList<InterceptRule>() {{
        add(new InterceptRule("/admin/page", SaHttpMethod.POST, "admin:list"));
    }};

    // 注册Sa-Token的注解拦截器，打开注解式鉴权功能
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaRouteInterceptor((request, response, handler) -> {

            for (InterceptRule rule : INTERCEPT_RULES) {
                SaRouter.match(rule.path).match(rule.method).check(() -> StpUtil.checkPermission(rule.permissionKey));
            }
        }));
    }
}
