package com.o11eh.servicedemo.admin.service.impl;

import com.o11eh.servicedemo.admin.service.PermissionService;
import com.o11eh.servicedemo.admin.service.RoleService;
import com.o11eh.servicedemo.admin.entry.Role;
import com.o11eh.servicedemo.admin.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 011eh
 * @since 2022-02-14
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private PermissionService permissionService;

    @Override
    public Long add(Role role) {
        role.insert();
        permissionService.grantPermissions(role.getId(), role.getPermissionIds());
        return role.getId();
    }

    @Override
    public Long updateRole(Role role) {
        role.updateById();
        permissionService.grantPermissions(role.getId(), role.getPermissionIds());
        return role.getId();
    }
}
