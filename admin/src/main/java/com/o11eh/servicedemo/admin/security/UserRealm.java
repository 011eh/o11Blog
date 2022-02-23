package com.o11eh.servicedemo.admin.security;

import cn.hutool.core.util.ObjectUtil;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.service.AdminService;
import com.o11eh.servicedemo.base.enums.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private AdminService adminService;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return true;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("进行认证");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        Admin admin = adminService.getByUsername(username);
        if (ObjectUtil.isNull(admin)) {
            throw new UnknownAccountException("用户名不存在");
        }
        if (admin.getStatus().equals(StatusEnum.Disable.getStatus())) {
            throw new DisabledAccountException("账号不可用");
        }
        return new SimpleAuthenticationInfo(admin, admin.getPassword(), "");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("进行授权");
        Admin admin = (Admin) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole(admin.getRole().getRoleKey());
        return info;
    }
}
