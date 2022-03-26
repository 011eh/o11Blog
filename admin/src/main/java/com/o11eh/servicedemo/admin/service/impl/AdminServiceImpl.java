package com.o11eh.servicedemo.admin.service.impl;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.config.BusinessException;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.entry.BaseEntry;
import com.o11eh.servicedemo.admin.entry.Role;
import com.o11eh.servicedemo.admin.mapper.AdminMapper;
import com.o11eh.servicedemo.admin.service.AdminService;
import com.o11eh.servicedemo.admin.service.PermissionService;
import com.o11eh.servicedemo.admin.service.RoleService;
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

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public Long add(Admin admin) {
        LambdaQueryWrapper<Admin> wrapper = Wrappers.<Admin>lambdaQuery()
                .select(Admin::getUsername).eq(Admin::getUsername, admin.getUsername());
        Admin adminInDB = getOne(wrapper);
        if (ObjectUtil.isNotNull(adminInDB)) {
            throw BusinessException.e("该用户已存在");
        }
        admin.insert();
        return admin.getId();
    }

    @Override
    public Page<Admin> page(Long current, Long size) {
        LambdaQueryWrapper<Admin> wrapper = Wrappers.<Admin>lambdaQuery().select(Admin.class,
                i -> !i.getColumn().equals("password"));
        Page<Admin> page = page(current, size, wrapper);
        page.getRecords().forEach(admin -> {
            Role role = roleService.getById(admin.getRoleId());
            admin.setRole(role);
        });
        return page;
    }

    @Override
    public Admin loginByUsername(String username) {

        Admin admin = getOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getUsername, username).last(LIMIT_1));
        if (ObjectUtil.isNull(admin)) {
            throw BusinessException.e("帐号或密码错误");
        }

        Role role = roleService.getOne(Wrappers.<Role>lambdaQuery().eq(BaseEntry::getId, admin.getRoleId())
                .last(LIMIT_1));
        admin.setRole(role);

        return admin;
    }

    @Override
    public Long updateAdmin(Admin admin) {
        this.updateById(admin);
        return admin.getId();
    }
}
