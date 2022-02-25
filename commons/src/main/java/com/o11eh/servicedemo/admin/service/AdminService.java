package com.o11eh.servicedemo.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.base.service.BaseService;

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

    Page<Admin> getPage(Long current, Long size);

    Admin getByUsername(String username);

    Long updateAdmin(Admin admin);
}
