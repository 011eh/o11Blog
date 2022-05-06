package com.o11eh.servicedemo.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("my-properties.aliyun")
public class aliyunProperties {

    private String endpoint ;
    private String bucketName ;
    private String accessKeyId ;
    private String accessKeySecret;

}
