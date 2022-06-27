package com.o11eh.servicedemo.front.config;

import cn.dev33.satoken.stp.StpUtil;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Optional;
import java.util.Properties;

@Configuration
public class JpaConfig {

    @Bean
    public AuditorAwareImpl auditorAwareImpl() {
        return new AuditorAwareImpl();
    }

    @Bean
    public IdentifierGenerator snowFlakeIdGenerator(Snowflake snowflake) {
        return new SnowFlakeIdGenerator(snowflake);
    }

    static class AuditorAwareImpl implements AuditorAware<String> {
        @Override
        public Optional<String> getCurrentAuditor() {
            String id = (String) StpUtil.getLoginIdDefaultNull();
            return Optional.ofNullable(id);
        }

    }

    @Component
    @AllArgsConstructor
    static class SnowFlakeIdGenerator implements IdentifierGenerator {

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
}
