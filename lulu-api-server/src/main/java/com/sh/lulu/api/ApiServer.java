package com.sh.lulu.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.sh.lulu")
@EntityScan(basePackages = "com.sh.lulu")
@EnableJpaRepositories(basePackages = "com.sh.lulu")
public class ApiServer {
    public static void main(String[] args) {
        SpringApplication.run(ApiServer.class,args);
    }
}
