package com.o11eh.servicedemo.servicebase.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("my-properties")
public class MyProperties {

    private Aliyun aliyun;

    @Data
    public static class Aliyun {
        private String endpoint;
        private String bucketName;
        private String accessKeyId;
        private String accessKeySecret;
    }

}
