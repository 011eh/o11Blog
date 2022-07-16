package com.o11eh.o11blog.article;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableDiscoveryClient

@EntityScan("com.o11eh.o11blog.servicebase.entity")
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@SpringBootApplication(scanBasePackages = {"com.o11eh.o11blog.article", "com.o11eh.o11blog.servicebase.config"},
        exclude = MybatisPlusAutoConfiguration.class)
public class ArticleApp {
    public static void main(String[] args) {
        SpringApplication.run(ArticleApp.class, args);
    }
}
