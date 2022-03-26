package com.o11eh.servicedemo.admin.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.o11eh.servicedemo.admin.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorizationC implements StpInterface {

    @Autowired
    private PermissionService permissionService;


    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return ((AuthInfo) StpUtil.getSession().get("authInfo")).getPermissionKeys();
    }


    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return null;
    }
}
