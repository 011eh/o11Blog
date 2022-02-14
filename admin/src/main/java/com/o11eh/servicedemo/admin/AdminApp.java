package com.o11eh.servicedemo.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 011eh
 * @since 2022/02/14 19:45
 */

@MapperScan("com.o11eh.servicedemo.commons.mapper")
@ComponentScan("com.o11eh.servicedemo.commons")
@ComponentScan("com.o11eh.servicedemo.servicebase")
@SpringBootApplication
public class AdminApp {
    public static void main(String[] args) {
        SpringApplication.run(AdminApp.class, args);
    }
}
