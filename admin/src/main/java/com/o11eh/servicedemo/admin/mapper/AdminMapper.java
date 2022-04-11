package com.o11eh.servicedemo.admin.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o11eh.servicedemo.admin.entry.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 管理员 Mapper 接口
 * </p>
 *
 * @author 011eh
 * @since 2022-02-14
 */
@Mapper
public interface AdminMapper extends BaseMapperO<Admin> {
    Page<Admin> selectPage(Page<Admin> page, @Param("ew") Wrapper<Admin> wrapper);
}
