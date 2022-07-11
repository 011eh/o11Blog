package com.o11eh.o11blog.servicebase.config;

import cn.hutool.core.lang.Snowflake;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;


@Setter
@Configuration
@ConditionalOnClass(DataSourceAutoConfiguration.class)
@ConfigurationProperties(prefix = "my-properties.snow-flake")
public class SnowFlakeConfig {

    String epochDate;
    long dataCenterId;
    long workerId;

    @Bean
    public Snowflake snowflakeIdGenerator() throws ParseException {
        return new Snowflake(new SimpleDateFormat("yyyy-MM-dd").parse(epochDate), dataCenterId, workerId, true);
    }
}
