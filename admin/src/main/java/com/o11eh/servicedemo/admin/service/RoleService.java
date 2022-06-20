package com.o11eh.servicedemo.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.servicebase.entity.PageReq;
import com.o11eh.servicedemo.admin.entity.Role;

import java.util.List;

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

    void deleteRole(List<String> ids);

    Page<Role> getPage(PageReq req);

    List<Role> dtoList();
}
