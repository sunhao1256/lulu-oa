package com.sh.lulu.auth.config;

import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.util.EngineUtil;
import org.camunda.bpm.spring.boot.starter.property.CamundaBpmProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AuthBpmnFilter implements Filter {
    private final CamundaBpmProperties camundaBpmProperties;

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // Current limitation: Only works for the default engine
        ProcessEngine engine = EngineUtil.lookupProcessEngine(camundaBpmProperties.getProcessEngineName());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication==null){
            chain.doFilter(request,response);
            return;
        }

        Object principal = authentication.getPrincipal();

        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        try {
            engine.getIdentityService().setAuthentication(username, getUserGroups(username));
            chain.doFilter(request, response);
        } finally {
            clearAuthentication(engine);
        }

    }

    @Override
    public void destroy() {

    }

    private void clearAuthentication(ProcessEngine engine) {
        engine.getIdentityService().clearAuthentication();
    }

    private List<String> getUserGroups(String userId) {

        List<String> groupIds;

        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        groupIds = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return groupIds;

    }
}
