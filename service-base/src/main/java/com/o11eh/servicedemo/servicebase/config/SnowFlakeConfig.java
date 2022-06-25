package com.o11eh.servicedemo.servicebase.config;

import cn.hutool.core.lang.Snowflake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Configuration
public class SnowFlakeConfig {

    @Value("${my-properties.snow-flake.date}")
    String date;

    @Value("${my-properties.snow-flake.dataCenterId}")
    long dataCenterId;

    @Value("${my-properties.snow-flake.workerId}")
    long workerId;

    @Bean
    public Snowflake snowflakeIdGenerator() throws ParseException {
        return new Snowflake(new SimpleDateFormat("yyyy-MM-dd").parse(date), dataCenterId, workerId, true);
    }
}
