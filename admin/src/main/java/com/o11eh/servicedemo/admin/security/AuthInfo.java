package com.o11eh.servicedemo.admin.security;

import cn.hutool.core.util.ObjectUtil;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.entry.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AuthInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private String username;
    private String nickName;
    private String avatar;

    private Long roleId;
    private String roleName;
    private String roleSummary;
    private List<String> permissionKeys;

    private AuthInfo(Admin admin) {
        userId = admin.getId();
        username = admin.getUsername();
        nickName = admin.getNickName();
        avatar = admin.getAvatar();
        roleId = admin.getRoleId();

        Role role = admin.getRole();
        if (ObjectUtil.isNotNull(role)) {
            roleName = role.getName();
            roleSummary = role.getSummary();
            permissionKeys = role.getPermissionKeys();
        }
    }

    public static AuthInfo create(Admin admin) {
        return new AuthInfo(admin);
    }
}
