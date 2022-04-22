package com.o11eh.servicedemo.admin.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.entry.PageReq;
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
    private AdminMapper adminMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public Admin login(String username, String password) {
        String encrypt = SaSecureUtil.md5BySalt(password, username);
        Admin admin = adminMapper.selectToLogin(Wrappers.<Admin>lambdaQuery()
                .eq(Admin::getUsername, username).eq(Admin::getPassword, encrypt));
        return admin;
    }

    @Override
    public Page<Admin> page(PageReq param) {

        Wrapper<Admin> wrapper = StrUtil.isBlank(param.getKeyword()) ?
                Wrappers.emptyWrapper() : Wrappers.<Admin>lambdaQuery().like(Admin::getUsername, param.getKeyword())
                .or(queryWrapper -> queryWrapper.like(Admin::getNickName, param.getKeyword()));
        return super.page(param.getCurrent(), param.getSize(), wrapper);
    }

    @Override
    public String create(Admin admin) {
        String password = "11111";
        admin.setPassword(SaSecureUtil.md5BySalt(password, admin.getUsername()));
        this.save(admin);
        return admin.getId();
    }

    @Override
    public String update(Admin admin) {
        this.updateById(admin);
        return admin.getId();
    }
}
