package com.o11eh.o11blog.servicebase.config;

import cn.hutool.core.lang.Snowflake;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;


@Setter
@Configuration
@ConditionalOnProperty(name = "my-properties.snow-flake.epochDate")
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
