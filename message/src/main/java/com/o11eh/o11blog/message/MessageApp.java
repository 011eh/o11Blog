package com.o11eh.o11blog.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MessageApp {
    public static void main(String[] args) {
        SpringApplication.run(MessageApp.class, args);
    }
}
