package com.o11eh.o11blog.servicebase.config.jpaconfig;

import cn.hutool.core.lang.Snowflake;
import lombok.AllArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.boot.model.relational.Database;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import java.io.Serializable;
import java.util.Properties;

@ConditionalOnBean(Snowflake.class)
@AllArgsConstructor
public class SnowFlakeIdGenerator implements IdentifierGenerator {

    private Snowflake snowflake;

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
        return snowflake.nextIdStr();
    }

    @Override
    public boolean supportsJdbcBatchInserts() {
        return IdentifierGenerator.super.supportsJdbcBatchInserts();
    }
}
