package com.o11eh.servicedemo.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.entity.BaseEntry;
import com.o11eh.servicedemo.servicebase.entry.PageReq;
import com.o11eh.servicedemo.admin.entity.Role;
import com.o11eh.servicedemo.admin.mapper.RoleMapper;
import com.o11eh.servicedemo.admin.service.PermissionService;
import com.o11eh.servicedemo.admin.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 011eh
 * @since 2022-02-14
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private PermissionService permissionService;

    public Page<Role> getPage(PageReq req) {
        Page<Role> page = this.page(new Page<>(req.getCurrent(), req.getSize()),
                Wrappers.<Role>lambdaQuery().like(StrUtil.isNotBlank(req.getKeyword()), Role::getName, req.getKeyword()));
        return page;
    }

    @Override
    public List<Role> dtoList() {
        return super.getDtoList(Wrappers.<Role>lambdaQuery().select(BaseEntry::getId, Role::getName));
    }

    @Override
    @Transactional
    public String create(Role role) {
        this.save(role);
        permissionService.grantPermissions(role.getId(), role.getPermissionKeys(), false);
        return role.getId();
    }

    @Override
    @Transactional
    public String updateRole(Role role) {
        this.updateById(role);
        permissionService.grantPermissions(role.getId(), role.getPermissionKeys(), true);
        return role.getId();
    }

    @Override
    @Transactional
    public void deleteRole(List<String> roleIds) {
        this.removeBatchByIds(roleIds);
        permissionService.revokePermissions(roleIds);
    }
}
