package com.o11eh.o11blog.admin.config;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.AllArgsConstructor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Configuration
@MapperScan("com.o11eh.o11blog.*.mapper")
@AllArgsConstructor
public class MybatisPlusConfig {

    private Snowflake snowflake;

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        return interceptor;
    }

    @Component
    public static class MetaObjectHandlerImpl implements MetaObjectHandler {
        @Override
        public void insertFill(MetaObject metaObject) {
            strictInsertFill(metaObject, "createdBy", () -> (String) StpUtil.getLoginIdDefaultNull(), String.class);
            strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
            strictInsertFill(metaObject, "updatedBy", () -> (String) StpUtil.getLoginIdDefaultNull(), String.class);
            strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            strictUpdateFill(metaObject, "updatedBy", () -> (String) StpUtil.getLoginIdDefaultNull(), String.class);
            strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        }
    }

    @Component
    public class CustomIdGenerator implements IdentifierGenerator {
        @Override
        public Long nextId(Object entity) {
            return snowflake.nextId();
        }
    }
}
