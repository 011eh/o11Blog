package com.o11eh.servicedemo.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.o11eh.servicedemo.admin.entry.Permission;
import com.o11eh.servicedemo.admin.mapper.PermissionMapper;
import com.o11eh.servicedemo.admin.service.PermissionService;
import com.o11eh.servicedemo.base.entry.BaseEntry;
import com.o11eh.servicedemo.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 011eh
 * @since 2022/02/27 12:21
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Override
    public List<Permission> getPermissionKeysByIds(List<Long> permissionIds) {
        LambdaQueryWrapper<Permission> wrapper = Wrappers.<Permission>lambdaQuery().select(Permission::getPermissionKey)
                .in(CollUtil.isNotEmpty(permissionIds), BaseEntry::getId, permissionIds);
        return this.list(wrapper);
    }

    @Override
    public List<Permission> getPermissions() {
        final int rootParentId = 0;

        List<Permission> permissions = this.list();
        Map<Long, List<Permission>> group = permissions.stream().collect(Collectors.groupingBy(Permission::getParentId));
        List<Permission> roots = group.remove(rootParentId);

        roots.stream().map(root -> {
            root.setChildren();
        });

        return null;
    }
}
