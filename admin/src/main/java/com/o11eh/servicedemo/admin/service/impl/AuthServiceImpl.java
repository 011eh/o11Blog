package com.o11eh.servicedemo.admin.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.o11eh.servicedemo.admin.config.AuthInfo;
import com.o11eh.servicedemo.admin.config.BusinessException;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.enums.Status;
import com.o11eh.servicedemo.admin.service.AdminService;
import com.o11eh.servicedemo.admin.service.AuthService;
import com.o11eh.servicedemo.admin.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public String login(String username, String password) {
        Admin admin = adminService.login(username, password);
        if (admin == null) {
            throw BusinessException.e("帐号或密码错误");
        }
        if (admin.getStatus() == Status.Disable) {
            throw BusinessException.e("账号被锁定");
        }

        StpUtil.login(username);
        AuthInfo authInfo = new AuthInfo(admin);
        authInfo.setPermission(permissionService.getAuthInfoByRoleId(admin.getRoleId()));
        SaSession session = StpUtil.getSession();
        session.set("authInfo", authInfo);
        return StpUtil.getTokenValue();
    }

}
