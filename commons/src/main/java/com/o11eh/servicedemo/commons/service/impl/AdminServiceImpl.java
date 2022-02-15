package com.o11eh.servicedemo.commons.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.commons.entry.Admin;
import com.o11eh.servicedemo.commons.mapper.AdminMapper;
import com.o11eh.servicedemo.commons.service.AdminService;
import com.o11eh.servicedemo.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员 服务实现类
 * </p>
 *
 * @author 011eh
 * @since 2022-02-14
 */
@Service
public class AdminServiceImpl extends BaseServiceImpl<AdminMapper, Admin> implements AdminService {
    @Override
    public Long add(Admin admin) {
        admin.insert();
        return admin.getId();
    }

    @Override
    public Page<Admin> getPage(Long current, Long size) {
        return page(current, size);
    }
}
