package com.sh.lulu.bpmn;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class BpmnServer {
    public static void main(String[] args) {
        SpringApplication.run(BpmnServer.class,args);
    }
}
