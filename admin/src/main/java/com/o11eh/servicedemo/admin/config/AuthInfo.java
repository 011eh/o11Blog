package com.o11eh.servicedemo.admin.config;

import cn.hutool.core.util.ObjectUtil;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.entry.Permission;
import com.o11eh.servicedemo.admin.entry.Role;
import com.o11eh.servicedemo.admin.enums.ResourceType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Comparator;
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
    private String roleId;
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
        Map<ResourceType, List<Permission>> typeMap = permissions.stream().sorted(Comparator.comparing(Permission::getSort))
                .collect(Collectors.groupingBy(Permission::getResourceType));

        setPermissionKeys(typeMap.remove(ResourceType.OPERATION).stream().map(Permission::getPermissionKey)
                .collect(Collectors.toList()));

        String rootParentId = "";
        Map<String, List<Permission>> parentIdMap = typeMap.values().stream().flatMap(Collection::stream)
                .collect(Collectors.groupingBy(permission ->
                        permission.getParentId() != null ? permission.getParentId() : rootParentId));
        parentIdMap.values().stream().flatMap(Collection::stream).
                forEach((router) -> router.setChildren(parentIdMap.get(router.getId())));

        setRouters(parentIdMap.get(rootParentId));
    }
}
