package com.o11eh.servicedemo.admin.service;

import com.o11eh.servicedemo.admin.entry.Permission;
import com.o11eh.servicedemo.admin.entry.vo.PermissionTreeVo;

import java.util.List;

/**
 * @author 011eh
 * @since 2022/02/27 12:21
 */
public interface PermissionService extends BaseService<Permission> {
    List<Permission> getAuthInfoByRoleId(String roleId);

    List<Permission> getPermissionList();

    void grantPermissions(String id, List<String> permissionIds);

    List<Permission> dtoList();

    Permission detail(String id);

    List<PermissionTreeVo> treeDtoList();
}
