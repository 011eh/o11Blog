package com.o11eh.servicedemo.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.o11eh.servicedemo.admin.entry.BaseEntry;
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
    public Permission detail(String id) {
        return getById(id);
    }

    @Override
    public List<Permission> getPermissionList() {
        List<Permission> list = this.list(Wrappers.<Permission>lambdaQuery()
                .select(BaseEntry::getId, Permission::getParentId, Permission::getName,
                        Permission::getPermissionKey, Permission::getSort, Permission::getResourceType, Permission::getStatus));

        String rootParentId = "";
        Map<String, List<Permission>> parentIdMap = list.stream().sorted(Comparator.comparing(Permission::getSort))
                .collect(Collectors.groupingBy(
                        permission -> permission.getParentId() != null ? permission.getParentId() : rootParentId,
                        LinkedHashMap::new, Collectors.toList()));

        parentIdMap.values().stream().flatMap(Collection::stream)
                .forEach(permission -> permission.setChildren(parentIdMap.get(permission.getId())));

        return parentIdMap.get(rootParentId);
    }

    @Override
    public List<Permission> getAuthInfoByRoleId(String roleId) {
        List<Permission> permissions = permissionMapper.selectPermissionByRoleId(roleId);
        return permissions;
    }

    @Override
    public List<String> getPermissionIdsGranted(String roleId) {
        List<String> permissionIds = permissionMapper.selectPermissionByRoleId(roleId)
                .stream().map(BaseEntry::getId).collect(Collectors.toList());
        return permissionIds;
    }

    @Override
    public void grantPermissions(String roleId, List<String> permissionIds, boolean doUpdate) {
        SqlSession session = SqlHelper.FACTORY.openSession(ExecutorType.BATCH, false);
        PermissionMapper mapper = session.getMapper(PermissionMapper.class);
        if (doUpdate) {
            mapper.deletePermissionGranted(roleId);
        }
        permissionIds.forEach(pId -> mapper.grantPermission(roleId, pId));
        session.commit();
    }

    @Override
    public void revokePermissions(List<String> roleIds) {
        SqlSession session = SqlHelper.FACTORY.openSession(ExecutorType.BATCH, false);
        PermissionMapper mapper = session.getMapper(PermissionMapper.class);
        roleIds.forEach(mapper::deletePermissionGranted);
        session.commit();
    }

    @Override
    public List<Permission> dtoList() {
        List<Permission> list = this.getDtoList(Wrappers.<Permission>lambdaQuery().select(BaseEntry::getId,
                Permission::getParentId, Permission::getResourceType, Permission::getName, Permission::getSort));
        list.sort(Comparator.comparing((Permission p) -> p.getResourceType().getSort())
                .thenComparing(Permission::getSort));
        return list;
    }

    public List<Permission> treeDtoList() {
        List<Permission> permissionList = this.dtoList();
        String rootParentId = "";
        Map<String, List<Permission>> parentIdMap = permissionList.stream()
                .collect(Collectors.groupingBy(
                        permission -> permission.getParentId() != null ? permission.getParentId() : rootParentId));

        parentIdMap.values().stream().flatMap(Collection::stream).forEach(permission -> {
            permission.setChildren(parentIdMap.get(permission.getId()));
        });

        return parentIdMap.get(rootParentId);
    }
}
