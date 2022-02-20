package com.o11eh.servicedemo.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 011eh
 * @since 2022/02/14 19:45
 */
@SpringBootApplication(scanBasePackages = {
        "com.o11eh.servicedemo.admin",
        "com.o11eh.servicedemo.servicebase"
})
public class AdminApp {
    public static void main(String[] args) {
        SpringApplication.run(AdminApp.class, args);
    }
}
