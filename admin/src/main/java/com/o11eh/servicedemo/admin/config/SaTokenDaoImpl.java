package com.o11eh.servicedemo.admin.config;

import cn.dev33.satoken.dao.SaTokenDaoRedisJackson;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Primary
@Component
public class SaTokenDaoImpl extends SaTokenDaoRedisJackson {

    @Autowired
    @Override
    public void init(RedisConnectionFactory connectionFactory) {
        super.init(connectionFactory);
        objectMapper.configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false);
    }
}
