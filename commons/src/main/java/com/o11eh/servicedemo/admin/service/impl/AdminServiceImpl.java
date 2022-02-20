package com.o11eh.servicedemo.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.mapper.AdminMapper;
import com.o11eh.servicedemo.admin.service.AdminService;
import com.o11eh.servicedemo.base.constants.ResultMessage;
import com.o11eh.servicedemo.base.exception.BusinessException;
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
        LambdaQueryWrapper<Admin> wrapper = wrapper().select(Admin::getUsername).eq(Admin::getUsername,
                admin.getUsername());
        Admin adminInDB = getOne(wrapper);
        if (ObjectUtil.isNotNull(adminInDB)) {
            throw BusinessException.e(ResultMessage.USERNAME_EXISTS);
        }
        admin.insert();
        return admin.getId();
    }

    @Override
    public Page<Admin> getPage(Long current, Long size) {
        return page(current, size);
    }
}
