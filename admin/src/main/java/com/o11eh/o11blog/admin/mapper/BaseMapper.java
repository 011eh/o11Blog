package com.o11eh.o11blog.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface BaseMapper {
    boolean recordExists(@Param("table") String table, @Param("id") String id);

    boolean columnUnique(@Param("table") String table, @Param("columnName") String columnName, @Param("value") Object value, @Param("id") String id);

    Set<String> selectIdIfExist(@Param("table") String table, @Param("ids") List<String> ids);
}
