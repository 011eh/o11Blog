package com.o11eh.servicedemo.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.o11eh.servicedemo.admin.entry.Permission;
import com.o11eh.servicedemo.admin.mapper.PermissionMapper;
import com.o11eh.servicedemo.admin.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 011eh
 * @since 2022/02/27 12:21
 */
@Service
@Slf4j
public class PermissionServiceImpl extends BaseServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getPermissionByRoleId(Long roleId) {
        return permissionMapper.selectPermission(roleId);
    }

    @Override
    public List<Permission> getPermissions() {
        Map<String, List<Permission>> parentIdMap = this.list().stream().sorted(Comparator.comparing(Permission::getSort)).collect(Collectors
                .groupingBy(Permission::getParentId, LinkedHashMap::new, Collectors.toList()));

        String rootParentId = "";
        parentIdMap.values().stream().flatMap(Collection::stream)
                .forEach(permission -> permission.setChildren(parentIdMap.get(permission.getId())));

        return parentIdMap.get(rootParentId);
    }

    @Override
    public Page<Permission> page(long current, long size) {
        return super.page(current, size);
    }

    @Override
    public void grantPermissions(String roleId, List<String> permissionIds) {
        SqlSession session = SqlHelper.FACTORY.openSession(ExecutorType.BATCH, false);
        PermissionMapper mapper = session.getMapper(PermissionMapper.class);
        mapper.deletePermission(roleId);
        permissionIds.forEach(pId -> mapper.insertPermission(roleId, pId));
        session.commit();
    }
}
