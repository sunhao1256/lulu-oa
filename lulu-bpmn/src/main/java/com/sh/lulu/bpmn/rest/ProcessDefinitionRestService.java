package com.sh.lulu.bpmn.rest;

import com.sh.lulu.auth.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/process-definition")
public class ProcessDefinitionRestService {
    private final RepositoryService repositoryService;
    private final UserRepository userRepository;

}
