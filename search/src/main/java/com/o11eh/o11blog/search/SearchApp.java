package com.o11eh.o11blog.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.o11eh.o11blog.servicebase.config", "com.o11eh.o11blog.search"},
        exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class})
public class SearchApp {
    public static void main(String[] args) {
        SpringApplication.run(SearchApp.class, args);
    }
}
