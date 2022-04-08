package com.o11eh.servicedemo.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.o11eh.servicedemo.admin.entry.BaseEntry;
import com.o11eh.servicedemo.admin.entry.Permission;
import com.o11eh.servicedemo.admin.entry.vo.PermissionTreeVo;
import com.o11eh.servicedemo.admin.enums.ResourceType;
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
    public List<Permission> getAuthInfoByRoleId(String roleId) {
        List<Permission> permissions = permissionMapper.selectPermissionByRoleId(roleId);
        return permissions;
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

    @Override
    public List<Permission> dtoList() {
        List<Permission> list = this.getDtoList(Wrappers.<Permission>lambdaQuery().select(BaseEntry::getId,
                Permission::getParentId, Permission::getResourceType, Permission::getName, Permission::getSort));
        list.sort(Comparator.comparing((Permission p) -> p.getResourceType().getSort())
                .thenComparing(Permission::getSort));
        return list;
    }

    public List<PermissionTreeVo> treeDtoList() {
        List<Permission> permissionList = this.dtoList();
        String rootParentId = "";
        Map<String, List<Permission>> parentIdMap = permissionList.stream()
                .collect(Collectors.groupingBy(
                        permission -> permission.getParentId() != null ? permission.getParentId() : rootParentId));

        Map<ResourceType, List<Permission>> typeMap = permissionList.stream().collect(Collectors.groupingBy(Permission::getResourceType, LinkedHashMap::new, Collectors.toList()));
        typeMap.remove(ResourceType.OPERATION);

        List<PermissionTreeVo> treeVoList = typeMap.values().stream().flatMap(Collection::stream).
                map(PermissionTreeVo::new).collect(Collectors.toList());

        treeVoList.forEach(treeVo -> treeVo.setChildren(parentIdMap.get(treeVo.getId())));
        return treeVoList;
    }

    @Override
    public Permission detail(String id) {
        return getById(id);
    }
}
