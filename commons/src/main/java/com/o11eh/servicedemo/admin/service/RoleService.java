package com.o11eh.servicedemo.admin.service;

import com.o11eh.servicedemo.admin.entry.Role;
import com.o11eh.servicedemo.base.service.BaseService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 011eh
 * @since 2022-02-14
 */
public interface RoleService extends BaseService<Role> {
    Long add(Role role);

    Long updateRole(Role role);
}
