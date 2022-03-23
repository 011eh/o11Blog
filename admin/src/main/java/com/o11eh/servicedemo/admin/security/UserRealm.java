package com.o11eh.servicedemo.admin.security;

import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.service.AdminService;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import com.o11eh.servicedemo.admin.enums.StatusEnum;

import java.util.List;

@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private AdminService adminService;

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("进行认证");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        Admin admin = adminService.getByUsername(username);

        if (admin.getStatus().equals(StatusEnum.Disable.getStatus())) {
            throw new DisabledAccountException();
        }

        ByteSource credentialsSalt = ByteSource.Util.bytes(admin.getUsername());

        return new SimpleAuthenticationInfo(AuthInfo.create(admin), admin.getPassword(), credentialsSalt, getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("进行授权");
        AuthInfo authInfo = (AuthInfo) principalCollection.getPrimaryPrincipal();
        List<String> permissionKeys = authInfo.getPermissionKeys();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (ObjectUtil.isNotNull(permissionKeys)) {
            info.addStringPermissions(permissionKeys);
        }
        return info;
    }
}
