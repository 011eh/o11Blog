package com.o11eh.servicedemo.admin.service;

import com.o11eh.servicedemo.admin.entry.Role;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 011eh
 * @since 2022-02-14
 */
public interface RoleService extends BaseService<Role> {
    String create(Role role);

    String updateRole(Role role);
}
