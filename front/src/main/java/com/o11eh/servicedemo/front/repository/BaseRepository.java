package com.o11eh.servicedemo.front.repository;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.o11eh.servicedemo.front.entity.BaseEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.Assert;

public interface BaseRepository<T extends BaseEntry> extends JpaRepository<T, String>, JpaSpecificationExecutor<T> {

    String ID_MUST_NOT_BE_NULL = "ID不能为空";
    String ENTITY_NULL = "实体数据不存在";

    default void updateById(T entityUpdate) {
        String id = entityUpdate.getId();
        Assert.notNull(id, ID_MUST_NOT_BE_NULL);

        T entity = getById(id);
        Assert.notNull(entity, ENTITY_NULL);

        BeanUtil.copyProperties(entityUpdate, entity, CopyOptions.create().ignoreNullValue().setIgnoreProperties("id"));
        this.save(entity);
    }
}
