package com.o11eh.o11blog.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.o11blog.admin.entity.Admin;
import com.o11eh.o11blog.servicebase.entity.PageReq;

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
