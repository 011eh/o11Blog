package com.o11eh.servicedemo.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.entry.PageReq;

/**
 * <p>
 * 管理员 服务类
 * </p>
 *
 * @author 011eh
 * @since 2022-02-14
 */
public interface AdminService extends BaseService<Admin> {

    String create(Admin admin);

    Admin login(String username);

    String update(Admin admin);

    Page<Admin> page(PageReq param);
}
