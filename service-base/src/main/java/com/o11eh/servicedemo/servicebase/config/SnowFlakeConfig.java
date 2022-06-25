package com.o11eh.servicedemo.servicebase.config;

import cn.hutool.core.lang.Snowflake;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Data
@Configuration
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
