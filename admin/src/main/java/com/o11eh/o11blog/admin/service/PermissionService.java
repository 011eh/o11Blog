package com.o11eh.o11blog.admin.service;

import com.o11eh.o11blog.admin.entity.Permission;

import java.util.List;

/**
 * @author 011eh
 * @since 2022/02/27 12:21
 */
public interface PermissionService extends BaseService<Permission> {
    List<Permission> getPermissionList();

    List<Permission> getAuthInfoByRoleId(String roleId);

    Permission detail(String id);

    void grantPermissions(String id, List<String> permissionIds, boolean doUpdate);

    List<Permission> getPermissionGranted(String roleId);

    void revokePermissions(List<String> ids);

    List<Permission> dtoList();

    List<Permission> treeDtoList();

}
