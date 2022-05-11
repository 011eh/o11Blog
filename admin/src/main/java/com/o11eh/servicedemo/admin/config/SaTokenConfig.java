package com.o11eh.servicedemo.admin.config;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaRouteInterceptor((request, response, handler) -> {
            SaRouter.match("/admin").match(SaHttpMethod.POST).check(() -> StpUtil.checkPermission("admin:create"));
            SaRouter.match("/admin").match(SaHttpMethod.PUT).check(() -> StpUtil.checkPermission("admin:update"));
            SaRouter.match("/admin").match(SaHttpMethod.DELETE).check(() -> StpUtil.checkPermission("admin:delete"));
            SaRouter.match("/admin/page").match(SaHttpMethod.POST).check(() -> StpUtil.checkPermission("admin:list"));

            SaRouter.match("/role").match(SaHttpMethod.POST).check(() -> StpUtil.checkPermission("role:create"));
            SaRouter.match("/role").match(SaHttpMethod.PUT).check(() -> StpUtil.checkPermission("role:update"));
            SaRouter.match("/role").match(SaHttpMethod.DELETE).check(() -> StpUtil.checkPermission("role:delete"));
            SaRouter.match("/role/page").match(SaHttpMethod.POST).check(() -> StpUtil.checkPermission("role:list"));

            SaRouter.match("/permission/{id}").notMatch("/permission/list").match(SaHttpMethod.GET).check(() -> StpUtil.checkPermission("permission:update"));
            SaRouter.match("/permission/list").match(SaHttpMethod.GET).check(() -> StpUtil.checkPermission("permission:list"));
            SaRouter.match("/permission").match(SaHttpMethod.POST).check(() -> StpUtil.checkPermission("permission:create"));
            SaRouter.match("/permission").match(SaHttpMethod.PUT).check(() -> StpUtil.checkPermission("permission:update"));
            SaRouter.match("/permission").match(SaHttpMethod.DELETE).check(() -> StpUtil.checkPermission("permission:delete"));

            SaRouter.match("/sysBase/roleDto").check(() -> StpUtil.checkPermissionOr("admin:create","admin:update"));
            SaRouter.match("/sysBase/permissionTree").check(() -> StpUtil.checkPermissionOr("role:create","role:update"));
            SaRouter.match("/sysBase/permissionDto").check(() -> StpUtil.checkPermissionOr("permission:create","permission:update"));

        }));
    }
}
