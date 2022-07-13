package com.o11eh.o11blog.member;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableDiscoveryClient

@EntityScan("com.o11eh.o11blog.servicebase.entity")
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@SpringBootApplication(scanBasePackages = {"com.o11eh.o11blog.member", "com.o11eh.o11blog.servicebase.config"},
        exclude = MybatisPlusAutoConfiguration.class)
public class MemberApp {
    public static void main(String[] args) {
        SpringApplication.run(MemberApp.class, args);
    }
}
