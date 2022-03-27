package com.o11eh.servicedemo.admin.service;

import com.o11eh.servicedemo.admin.entry.Permission;

import java.util.List;

/**
 * @author 011eh
 * @since 2022/02/27 12:21
 */
public interface PermissionService extends BaseService<Permission> {
    List<Permission> getPermissionByRoleId(Long roleId);

    List<Permission> getPermissions();

    void grantPermissions(String id, List<String> permissionIds);
}
