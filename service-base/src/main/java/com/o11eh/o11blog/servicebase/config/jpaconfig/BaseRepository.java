package com.o11eh.o11blog.servicebase.config.jpaconfig;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.o11eh.o11blog.servicebase.entity.BaseEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.Assert;

@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, String> {

    String ID_MUST_NOT_BE_NULL = "ID不能为空";
    String ENTITY_NULL = "实体数据不存在";

    default void updateById(T entityUpdate) {
        BaseEntry baseEntry = (BaseEntry) entityUpdate;
        String id = baseEntry.getId();
        Assert.notNull(id, ID_MUST_NOT_BE_NULL);

        T entity = getById(id);
        Assert.notNull(entity, ENTITY_NULL);

        BeanUtil.copyProperties(entity, entity, CopyOptions.create().ignoreNullValue().setIgnoreProperties("id"));
        this.save(entity);
    }
}
