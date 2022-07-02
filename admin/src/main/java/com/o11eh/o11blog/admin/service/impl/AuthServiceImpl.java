package com.o11eh.o11blog.admin.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.o11eh.o11blog.admin.config.AuthInfo;
import com.o11eh.o11blog.servicebase.config.BusinessException;
import com.o11eh.o11blog.admin.entity.Admin;
import com.o11eh.o11blog.servicebase.enums.Status;
import com.o11eh.o11blog.admin.service.AdminService;
import com.o11eh.o11blog.admin.service.AuthService;
import com.o11eh.o11blog.admin.service.PermissionService;
import com.o11eh.o11blog.servicebase.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

        adminService.update(Wrappers.<Admin>update()
                .eq("id", admin.getId())
                .set("last_login_time", LocalDateTime.now())
                .set("last_login_ip", HttpUtil.getIPAddress()));

        StpUtil.login(admin.getUsername());
        AuthInfo authInfo = new AuthInfo(admin);
        authInfo.setPermission(permissionService.getAuthInfoByRoleId(admin.getRoleId()));
        SaSession session = StpUtil.getSession();
        session.set("authInfo", authInfo);
        return StpUtil.getTokenValue();
    }

}
