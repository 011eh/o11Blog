package com.o11eh.servicedemo.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.o11eh.servicedemo.admin.entry.Permission;
import com.o11eh.servicedemo.admin.enums.ResourceType;
import com.o11eh.servicedemo.admin.mapper.PermissionMapper;
import com.o11eh.servicedemo.admin.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
        List<Permission> permissions = permissionMapper.selectKeys(roleId);

        Map<ResourceType, List<Permission>> resources = permissions.stream()
                .collect(Collectors.groupingBy(Permission::getResourceType));

        List<Permission> menu = resources.get(ResourceType.MENU);
        Map<Long, List<Permission>> menuMap = menu.stream().collect(Collectors.groupingBy(Permission::getParentId));

        List<Permission> operations = resources.get(ResourceType.OPERATION);

        log.info(String.valueOf(menu));
        return permissions;
    }

    @Override
    public List<Permission> getPermissions() {
        final long rootParentId = 0;

        List<Permission> permissions = this.list();
        Map<Long, List<Permission>> group = permissions.stream().collect(Collectors.groupingBy(Permission::getParentId));
        List<Permission> roots = group.remove(rootParentId);

        roots.forEach(root -> root.setChildren(group.get(root.getId())));

        return roots;
    }

    @Override
    public void grantPermissions(Long roleId, List<Long> permissionIds) {
        SqlSession session = SqlHelper.FACTORY.openSession(ExecutorType.BATCH, false);
        PermissionMapper mapper = session.getMapper(PermissionMapper.class);
        mapper.deletePermission(roleId);
        permissionIds.forEach(pId -> mapper.insertPermission(roleId, pId));
        session.commit();
    }
}
