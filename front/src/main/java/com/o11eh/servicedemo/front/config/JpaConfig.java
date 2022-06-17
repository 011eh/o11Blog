package com.o11eh.servicedemo.front.config;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class JpaConfig {

    static class AuditorAwareImpl implements AuditorAware<String> {
        @Override
        public Optional<String> getCurrentAuditor() {
            String id = (String) StpUtil.getLoginIdDefaultNull();
            return Optional.ofNullable(id);
        }
    }

    @Bean
    public AuditorAwareImpl auditorAwareImpl() {
        return new AuditorAwareImpl();
    }
}
