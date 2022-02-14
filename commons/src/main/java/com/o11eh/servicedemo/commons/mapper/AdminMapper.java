package com.o11eh.servicedemo.commons.mapper;

import com.o11eh.servicedemo.base.mapper.BaseMapperO;
import com.o11eh.servicedemo.commons.entry.Admin;
import org.apache.ibatis.annotations.Mapper;

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

}
