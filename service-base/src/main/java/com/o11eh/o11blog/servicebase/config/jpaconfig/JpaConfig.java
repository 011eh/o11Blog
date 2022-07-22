package com.o11eh.o11blog.servicebase.config.jpaconfig;

import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Snowflake;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@ConditionalOnBean(Snowflake.class)
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
            String id = null;
            try {
                id = (String) StpUtil.getLoginIdDefaultNull();
            } catch (SaTokenException e) {

            }
            return Optional.ofNullable(id);
        }

    }

}
