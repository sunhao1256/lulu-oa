package com.sh.lulu.bpmn.apiserver;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.cockpit.impl.web.bootstrap.CockpitContainerBootstrap;
import org.camunda.bpm.spring.boot.starter.property.CamundaBpmProperties;
import org.camunda.bpm.spring.boot.starter.property.WebappProperty;
import org.camunda.bpm.spring.boot.starter.webapp.CamundaBpmWebappInitializer;
import org.camunda.bpm.tasklist.impl.web.TasklistApplication;
import org.camunda.bpm.tasklist.impl.web.bootstrap.TasklistContainerBootstrap;
import org.camunda.bpm.webapp.impl.engine.EngineRestApplication;
import org.camunda.bpm.webapp.impl.util.ServletContextUtil;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.Collections;

@Slf4j
public class CustomCamundaBpmWebappInitializer extends CamundaBpmWebappInitializer {
    private final CamundaBpmProperties properties;

    public CustomCamundaBpmWebappInitializer(CamundaBpmProperties properties) {
        super(properties);
        this.properties = properties;
    }

    private ServletContext servletContext;

    public void onStartup(ServletContext servletContext) {
        this.servletContext = servletContext;
        servletContext.addListener(new CockpitContainerBootstrap());
        servletContext.addListener(new TasklistContainerBootstrap());

        WebappProperty webapp = this.properties.getWebapp();
        String applicationPath = webapp.getApplicationPath();
        ServletContextUtil.setAppPath(applicationPath, servletContext);
        this.registerServlet("Cockpit Api", CustomApplication.CustomCockpitApplication.class, applicationPath + "/api/cockpit/*");
        this.registerServlet("Tasklist Api", CustomApplication.CustomTaskListApplication.class, applicationPath + "/api/tasklist/*");
        this.registerServlet("Engine Api", CustomApplication.CustomEngineRestApplication.class, applicationPath + "/api/engine/*");
    }

    private ServletRegistration registerServlet(String servletName, Class<?> applicationClass, String... urlPatterns) {
        ServletRegistration servletRegistration = this.servletContext.getServletRegistration(servletName);
        if (servletRegistration == null) {
            servletRegistration = this.servletContext.addServlet(servletName, ServletContainer.class);
            ((ServletRegistration) servletRegistration).addMapping(urlPatterns);
            ((ServletRegistration) servletRegistration).setInitParameters(Collections.singletonMap("javax.ws.rs.Application", applicationClass.getName()));
            log.debug("Servlet {} for URL {} registered.", servletName, urlPatterns);
        }

        return (ServletRegistration) servletRegistration;
    }

}
