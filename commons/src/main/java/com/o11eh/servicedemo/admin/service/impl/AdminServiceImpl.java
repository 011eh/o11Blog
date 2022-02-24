package com.o11eh.servicedemo.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.entry.Role;
import com.o11eh.servicedemo.admin.mapper.AdminMapper;
import com.o11eh.servicedemo.admin.service.AdminService;
import com.o11eh.servicedemo.admin.service.RoleService;
import com.o11eh.servicedemo.base.constants.ResultMessage;
import com.o11eh.servicedemo.base.entry.BaseEntry;
import com.o11eh.servicedemo.base.exception.BusinessException;
import com.o11eh.servicedemo.base.service.impl.BaseServiceImpl;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
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

    public static final int HASH_ITERATIONS = 5;
    @Autowired
    private RoleService roleService;

    @Override
    public Long add(Admin admin) {
        LambdaQueryWrapper<Admin> wrapper = wrapper().select(Admin::getUsername).eq(Admin::getUsername,
                admin.getUsername());
        Admin adminInDB = getOne(wrapper);
        if (ObjectUtil.isNotNull(adminInDB)) {
            throw BusinessException.e(ResultMessage.USERNAME_EXISTS);
        }
        ByteSource credentialsSalt = ByteSource.Util.bytes(admin.getUsername());
        String password = new SimpleHash(Md5Hash.ALGORITHM_NAME, admin.getPassword(), credentialsSalt,
                HASH_ITERATIONS).toString();
        admin.setPassword(password);
        admin.insert();
        return admin.getId();
    }

    @Override
    public Page<Admin> getPage(Long current, Long size) {
        return page(current, size);
    }

    @Override
    public Admin getByUsername(String username) {
        Admin admin = getOne(wrapper().eq(Admin::getUsername, username).last(LIMIT_1));
        if (ObjectUtil.isNotNull(admin)) {
            Role role = roleService.getOne(roleService.wrapper().eq(BaseEntry::getId, admin.getRoleId()).last(LIMIT_1));
            admin.setRole(role);
        }
        return admin;
    }
}
