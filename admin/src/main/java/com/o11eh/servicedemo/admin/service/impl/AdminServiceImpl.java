package com.o11eh.servicedemo.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.config.BusinessException;
import com.o11eh.servicedemo.admin.entry.Admin;
import com.o11eh.servicedemo.admin.entry.BaseEntry;
import com.o11eh.servicedemo.admin.entry.PageReq;
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
    private AdminMapper adminMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public Admin login(String username) {

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
    public Page<Admin> page(PageReq param) {

        Wrapper<Admin> wrapper = Wrappers.emptyWrapper();
        if (StrUtil.isNotBlank(param.getKeyword())) {
            wrapper = Wrappers.<Admin>lambdaQuery()
                    .like(Admin::getUsername, param.getKeyword())
                    .or(queryWrapper -> queryWrapper.like(Admin::getNickName, param.getKeyword()));
        }
        Page<Admin> page = adminMapper.selectPage(new Page<>(param.getCurrent(), param.getSize()), wrapper);
        return super.page(param.getCurrent(), param.getSize(), wrapper);
    }

    @Override
    public String create(Admin admin) {
        LambdaQueryWrapper<Admin> wrapper = Wrappers.<Admin>lambdaQuery()
                .select(Admin::getUsername).eq(Admin::getUsername, admin.getUsername());
        Admin adminInDB = getOne(wrapper);
        if (ObjectUtil.isNotNull(adminInDB)) {
            throw BusinessException.e("该用户已存在");
        }
        this.save(admin);
        return admin.getId();
    }

    @Override
    public String update(Admin admin) {
        this.updateById(admin);
        return admin.getId();
    }
}
