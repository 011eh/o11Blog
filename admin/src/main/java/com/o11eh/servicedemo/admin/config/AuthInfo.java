package com.o11eh.servicedemo.admin.config;

import cn.hutool.core.util.ObjectUtil;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.entry.Permission;
import com.o11eh.servicedemo.admin.entry.Role;
import com.o11eh.servicedemo.admin.enums.ResourceType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class AuthInfo {

    private static final long serialVersionUID = 1L;

    private String nickName;
    private String roleName;
    private String avatar;
    private Long roleId;
    private List<String> permissionKeys;
    private List<Permission> routers;

    public AuthInfo(Admin admin) {
        nickName = admin.getNickName();
        avatar = admin.getAvatar();
        roleId = admin.getRoleId();

        Role role = admin.getRole();
        if (ObjectUtil.isNotNull(role)) {
            roleName = role.getName();
        }
    }

    public void setPermission(List<Permission> permissions) {
        Map<ResourceType, List<Permission>> permissionMap = permissions.stream()
                .collect(Collectors.groupingBy(Permission::getResourceType));

        Map<Long, List<Permission>> parentIdMap = permissionMap.get(ResourceType.MENU).stream()
                .collect(Collectors.groupingBy(Permission::getParentId));
        List<String> permissionKeys = permissions.stream().map(Permission::getPermissionKey)
                .collect(Collectors.toList());

        List<Permission> routers = parentIdMap.values().stream().flatMap(Collection::stream).
                peek(router -> router.setChildren(parentIdMap.get(router.getId())))
                .filter(permission -> permission.getParentId() == 0L).collect(Collectors.toList());

        setPermissionKeys(permissionKeys);
        setRouters(routers);
    }
}
