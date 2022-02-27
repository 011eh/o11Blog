package com.o11eh.servicedemo.admin.service.impl;

import com.o11eh.servicedemo.admin.entry.Role;
import com.o11eh.servicedemo.admin.mapper.RoleMapper;
import com.o11eh.servicedemo.admin.service.RoleService;
import com.o11eh.servicedemo.base.service.impl.BaseServiceImpl;
import com.o11eh.servicedemo.base.utils.JsonUtl;
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

    @Override
    public Long add(Role role) {
        String permissionKeys = JsonUtl.ObjectToJson(role.getPermissionKeyList());
        role.setPermissionIds(permissionKeys);
        role.insert();
        return role.getId();
    }

    @Override
    public Long updateRole(Role role) {
        String permissionKeys = JsonUtl.ObjectToJson(role.getPermissionKeyList());
        role.setPermissionIds(permissionKeys);
        role.updateById();
        return role.getId();
    }
}
