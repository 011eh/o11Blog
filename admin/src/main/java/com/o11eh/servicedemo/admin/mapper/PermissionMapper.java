package com.o11eh.servicedemo.admin.mapper;

import com.o11eh.servicedemo.admin.entry.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 011eh
 * @since 2022/02/27 12:22
 */
@Mapper
public interface PermissionMapper extends BaseMapperO<Permission> {
    List<Permission> selectKeys(Long roleId);

    void insertPermission(@Param("roleId") Long id, @Param("pId") Long permissionId);

    void deletePermission(Long roleId);
}
