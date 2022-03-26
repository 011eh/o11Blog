package com.o11eh.servicedemo.admin.service;

import com.o11eh.servicedemo.admin.entry.Admin;

/**
 * <p>
 * 管理员 服务类
 * </p>
 *
 * @author 011eh
 * @since 2022-02-14
 */
public interface AdminService extends BaseService<Admin> {

    Long add(Admin admin);

    Admin loginByUsername(String username);

    Long updateAdmin(Admin admin);

}
