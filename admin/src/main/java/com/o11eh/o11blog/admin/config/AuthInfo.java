package com.o11eh.o11blog.admin.config;

import com.o11eh.o11blog.admin.entity.Admin;
import com.o11eh.o11blog.admin.entity.Permission;
import com.o11eh.o11blog.servicebase.enums.ResourceType;
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

    private String userId;
    private String nickName;
    private String roleId;
    private String roleName;
    private String avatar;
    private List<String> permissionKeys;
    private List<Permission.RouterInfo> routers;

    public AuthInfo(Admin admin) {
        userId = admin.getId();
        nickName = admin.getNickName();
        avatar = admin.getAvatar();
        roleId = admin.getRoleId();
        roleName = admin.getRoleName();
    }

    public void setPermission(List<Permission> permissions) {
        Map<ResourceType, List<Permission>> typeMap = permissions.stream().sorted(Comparator.comparing(Permission::getSort))
                .collect(Collectors.groupingBy(Permission::getResourceType));

        List<Permission> operations = typeMap.remove(ResourceType.OPERATION);
        if (operations != null) {
            setPermissionKeys(operations.stream().map(Permission::getPermissionKey)
                    .collect(Collectors.toList()));
        }
        String rootParentId = "";
        Map<String, List<Permission.RouterInfo>> parentIdMap = typeMap.values().stream().flatMap(Collection::stream)
                .map(permission -> {
                    Permission.RouterInfo routerInfo = permission.getRouterInfo();
                    routerInfo.setName(permission.getName());
                    routerInfo.setId(permission.getId());
                    routerInfo.setParentId(permission.getParentId());
                    return routerInfo;
                }).collect(Collectors.groupingBy(routerInfo -> routerInfo.getParentId() != null ?
                        routerInfo.getParentId() : rootParentId));
        parentIdMap.values().stream().flatMap(Collection::stream).
                forEach((router) -> router.setChildren(parentIdMap.get(router.getId())));

        setRouters(parentIdMap.get(rootParentId));
    }
}
