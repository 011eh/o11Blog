package com.o11eh.servicedemo.front;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@SpringBootApplication(scanBasePackages = {"com.o11eh.servicedemo.front", "com.o11eh.servicedemo.servicebase.config"},
        exclude = MybatisPlusAutoConfiguration.class)
public class FrontApp {
    public static void main(String[] args) {
        SpringApplication.run(FrontApp.class, args);
    }
}
