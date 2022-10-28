package com.sh.lulu.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.sh.lulu")
public class ApiServer {
    public static void main(String[] args) {
        SpringApplication.run(ApiServer.class,args);
    }
}
