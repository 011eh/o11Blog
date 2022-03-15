package com.o11eh.servicedemo.admin.security;

import cn.hutool.core.util.ObjectUtil;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.service.AdminService;
import com.o11eh.servicedemo.admin.service.PermissionService;
import com.o11eh.servicedemo.base.enums.StatusEnum;
import com.o11eh.servicedemo.base.utils.JwtUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class JwtRealm extends AuthorizingRealm {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof BearerToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken bearerToken) throws AuthenticationException {
        String token = (String) bearerToken.getCredentials();
        String username = JwtUtil.getUsername(token);

        Admin admin = adminService.getByUsername(username);
        if (ObjectUtil.isNull(admin)) {
            throw new UnknownAccountException("用户名不存在");
        }
        if (admin.getStatus().equals(StatusEnum.Disable.getStatus())) {
            throw new DisabledAccountException("账号不可用");
        }
        return new SimpleAuthenticationInfo(admin, token, getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Admin admin = (Admin) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        return info;
    }
}
