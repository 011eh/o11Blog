package com.o11eh.servicedemo.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.entry.Role;
import com.o11eh.servicedemo.admin.mapper.RoleMapper;
import com.o11eh.servicedemo.admin.service.PermissionService;
import com.o11eh.servicedemo.admin.service.RoleService;
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
    public Page<Role> page(long current, long size) {
        Page<Role> page = super.page(current, size);
        return page;
    }

    @Override
    public String create(Role role) {
        this.save(role);
        permissionService.grantPermissions(role.getId(), role.getPermissionIds());
        return role.getId();
    }

    @Override
    public String updateRole(Role role) {
        this.updateById(role);
        permissionService.grantPermissions(role.getId(), role.getPermissionIds());
        return role.getId();
    }
}
