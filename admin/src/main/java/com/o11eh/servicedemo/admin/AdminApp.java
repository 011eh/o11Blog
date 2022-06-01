package com.o11eh.servicedemo.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author 011eh
 * @since 2022/02/14 19:45
 */
@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.o11eh.servicedemo.admin", "com.o11eh.servicedemo.servicebase.config"})
public class AdminApp {
    public static void main(String[] args) {
        SpringApplication.run(AdminApp.class, args);
    }
}
