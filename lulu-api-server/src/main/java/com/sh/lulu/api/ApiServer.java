package com.sh.lulu.api;

import org.camunda.bpm.spring.boot.starter.webapp.CamundaBpmWebappAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {CamundaBpmWebappAutoConfiguration.class})
@ComponentScan(basePackages = "com.sh.lulu")
@EntityScan(basePackages = "com.sh.lulu")
@EnableJpaRepositories(basePackages = "com.sh.lulu")
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class ApiServer {
    public static void main(String[] args) {
        SpringApplication.run(ApiServer.class, args);
    }
}
