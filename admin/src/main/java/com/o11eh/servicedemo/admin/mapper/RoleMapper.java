package com.o11eh.servicedemo.admin.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 011eh
 * @since 2022-02-14
 */
@Mapper
public interface RoleMapper extends BaseMapperO<Role> {
    Page<Role> selectPage(Page<Role> page);
}
