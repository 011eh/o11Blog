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

    List<Permission> selectPermissionByRoleId(String roleId);

    void insertPermission(@Param("roleId") String id, @Param("pId") String permissionId);

    void deletePermission(String roleId);

}
