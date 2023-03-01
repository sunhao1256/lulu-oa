package com.sh.lulu.api.rest.sub.processDefinitionResource;

import com.sh.lulu.api.rest.dto.repository.ProcessDefinitionDiagramDto;
import com.sh.lulu.api.rest.dto.runtime.ProcessInstanceDto;
import com.sh.lulu.api.rest.dto.runtime.ProcessInstanceWithVariablesDto;
import com.sh.lulu.api.rest.dto.runtime.StartProcessInstanceDto;
import com.sh.lulu.api.rest.util.URLEncodingUtil;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.AuthorizationException;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.exception.NotFoundException;
import org.camunda.bpm.engine.impl.util.IoUtil;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.camunda.bpm.engine.runtime.ProcessInstantiationBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/process-definition/{id}")
public class ProcessDefinitionResource {
    private final RepositoryService repositoryService;
    private final RuntimeService runtimeService;

    @GetMapping("/xml")
    ProcessDefinitionDiagramDto getProcessDefinitionBpmn20Xml(@PathVariable("id") String processDefinitionId) {

        InputStream processModelIn = null;
        try {
            processModelIn = repositoryService.getProcessModel(processDefinitionId);
            byte[] processModel = IoUtil.readInputStream(processModelIn, "processModelBpmn20Xml");
            return ProcessDefinitionDiagramDto.create(processDefinitionId, new String(processModel, "UTF-8"));
        } catch (AuthorizationException e) {
            throw e;
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No matching definition with id " + processDefinitionId);
        } catch (UnsupportedEncodingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } finally {
            IoUtil.closeSilently(processModelIn);
        }
    }

    @GetMapping("/diagram")
    HttpEntity<?> getProcessDefinitionDiagram(
            @PathVariable("id") String processDefinitionId
    ) {
        ProcessDefinition definition = repositoryService.getProcessDefinition(processDefinitionId);
        InputStream processDiagram = repositoryService.getProcessDiagram(processDefinitionId);
        if (processDiagram == null) {
            return ResponseEntity.EMPTY;
        } else {
            String fileName = definition.getDiagramResourceName();
            return ResponseEntity.ok().header("Content-Disposition", URLEncodingUtil.buildAttachmentValue(fileName))
                    .contentType(getMediaTypeForFileSuffix(fileName))
                    .body(processDiagram);
        }
    }

    public static MediaType getMediaTypeForFileSuffix(String fileName) {
        String mediaType = "application/octet-stream"; // default
        if (fileName != null) {
            fileName = fileName.toLowerCase();
            if (fileName.endsWith(".png")) {
                mediaType = MediaType.IMAGE_PNG_VALUE;
            } else if (fileName.endsWith(".svg")) {
                mediaType = "image/svg+xml";
            } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                mediaType = "image/jpeg";
            } else if (fileName.endsWith(".gif")) {
                mediaType = "image/gif";
            } else if (fileName.endsWith(".bmp")) {
                mediaType = "image/bmp";
            }
        }
        return MediaType.parseMediaType(mediaType);
    }


    @PostMapping("/start")
    ProcessInstanceDto startProcessInstance(
            @PathVariable("id") String processDefinitionId,
            @RequestBody StartProcessInstanceDto parameters
    ) {
        ProcessInstanceWithVariables instance = null;
        try {
            instance = startProcessInstanceAtActivities(parameters, processDefinitionId);
        } catch (AuthorizationException e) {
            throw e;

        } catch (ProcessEngineException e) {
            String errorMessage = String.format("Cannot instantiate process definition %s: %s", processDefinitionId, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);

        }
        ProcessInstanceDto result;
        if (parameters.isWithVariablesInReturn()) {
            result = ProcessInstanceWithVariablesDto.fromProcessInstance(instance);
        } else {
            result = ProcessInstanceDto.fromProcessInstance(instance);
        }
        return result;

    }

    protected ProcessInstanceWithVariables startProcessInstanceAtActivities(StartProcessInstanceDto dto, String processDefinitionId) {
        Map<String, Object> processInstanceVariables = dto.getVariables();
        String businessKey = dto.getBusinessKey();
        String caseInstanceId = dto.getCaseInstanceId();


        ProcessInstantiationBuilder instantiationBuilder = runtimeService
                .createProcessInstanceById(processDefinitionId)
                .businessKey(businessKey)
                .caseInstanceId(caseInstanceId)
                .setVariables(processInstanceVariables);

        return instantiationBuilder.executeWithVariablesInReturn(dto.isSkipCustomListeners(), dto.isSkipIoMappings());
    }

}
