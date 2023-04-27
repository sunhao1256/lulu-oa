package com.sh.lulu.bpmn.apiserver;

import org.camunda.bpm.spring.boot.starter.CamundaBpmAutoConfiguration;
import org.camunda.bpm.spring.boot.starter.property.CamundaBpmProperties;
import org.camunda.bpm.spring.boot.starter.webapp.CamundaBpmWebappInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@ConditionalOnProperty(
        prefix = "camunda.bpm.webapp",
        name = {"enabled"},
        matchIfMissing = true
)
@ConditionalOnWebApplication
@AutoConfigureAfter({CamundaBpmAutoConfiguration.class})
public class ApiServerConfig implements WebMvcConfigurer {
    @Autowired
    private CamundaBpmProperties properties;

    public ApiServerConfig() {
    }

    @Bean
    public CamundaBpmWebappInitializer camundaBpmWebappInitializer() {
        return new CustomCamundaBpmWebappInitializer(this.properties);
    }

}
