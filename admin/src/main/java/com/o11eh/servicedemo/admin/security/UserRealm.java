package com.o11eh.servicedemo.admin.security;

import cn.hutool.core.util.ObjectUtil;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.entry.Role;
import com.o11eh.servicedemo.admin.service.AdminService;
import com.o11eh.servicedemo.admin.service.PermissionService;
import com.o11eh.servicedemo.servicebase.enums.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

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

        if (ObjectUtil.isNull(admin)) {
            throw new UnknownAccountException("用户名不存在");
        }
        if (admin.getStatus().equals(StatusEnum.Disable.getStatus())) {
            throw new DisabledAccountException("账号不可用");
        }

        ByteSource credentialsSalt = ByteSource.Util.bytes(admin.getUsername());
        return new SimpleAuthenticationInfo(admin, admin.getPassword(), credentialsSalt, getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("进行授权");
        Admin admin = (Admin) principalCollection.getPrimaryPrincipal();
        Role role = admin.getRole();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (ObjectUtil.isNotNull(role)) {
            List<String> keys = role.getPermissionKeyList();
            info.addStringPermissions(keys);
        }
        return info;
    }
}
