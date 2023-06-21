package com.sh.lulu.bpmn.rest.history;

import com.sh.lulu.bpmn.repo.cmd.GetDeployedHistoricTaskFormCmd;
import com.sh.lulu.bpmn.repo.cmd.GetHistoricTaskFormCmd;
import org.apache.commons.io.IOUtils;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;

import java.io.IOException;
import java.io.InputStream;

@RequestMapping("/history/task")
@RestController
public class Task {
    @Autowired
    CommandExecutor commandExecutor;

    @GetMapping("/{id}/deployed-form")
    public ResponseEntity<?> DeployedForm(@PathVariable String id) throws IOException {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(IOUtils.toByteArray(commandExecutor.execute(new GetDeployedHistoricTaskFormCmd(id))));
    }

    @GetMapping("/{id}/form-data")
    public TaskFormData FormData(@PathVariable String id) {
        return commandExecutor.execute(new GetHistoricTaskFormCmd(id));
    }
}
