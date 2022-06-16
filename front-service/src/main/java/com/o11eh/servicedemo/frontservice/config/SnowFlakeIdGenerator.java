package com.o11eh.servicedemo.frontservice.config;

import cn.hutool.core.util.IdUtil;
import com.o11eh.servicedemo.frontservice.entity.BaseEntry;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.boot.model.relational.Database;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class SnowFlakeIdGenerator implements IdentifierGenerator {
    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        IdentifierGenerator.super.configure(type, params, serviceRegistry);
    }

    @Override
    public void registerExportables(Database database) {
        IdentifierGenerator.super.registerExportables(database);
    }

    @Override
    public void initialize(SqlStringGenerationContext context) {
        IdentifierGenerator.super.initialize(context);
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return IdUtil.getSnowflakeNextIdStr();
    }

    @Override
    public boolean supportsJdbcBatchInserts() {
        return IdentifierGenerator.super.supportsJdbcBatchInserts();
    }
}
