package com.o11eh.servicedemo.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.servicebase.entry.PageReq;

import java.util.List;

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

    Admin login(String username, String password);

    String update(Admin admin);

    Page<Admin> page(PageReq param);

    List<Admin> dtoList();

    void resetPassword(String oldPassword, String newPassword);

    void resetToDefaultPassword(String userId);
}
