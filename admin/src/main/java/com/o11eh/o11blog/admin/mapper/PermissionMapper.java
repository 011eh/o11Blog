package com.o11eh.o11blog.admin.mapper;

import com.o11eh.o11blog.admin.entity.Permission;
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

    List<Permission> selectPermissionGranted(String roleId);

    void grantPermission(@Param("roleId") String id, @Param("permissionId") String permissionId);

    void deletePermissionGranted(String roleId);

}
