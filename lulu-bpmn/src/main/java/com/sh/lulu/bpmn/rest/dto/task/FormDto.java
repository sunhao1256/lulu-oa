package com.sh.lulu.bpmn.rest.dto.task;

import org.camunda.bpm.engine.form.CamundaFormRef;
import org.camunda.bpm.engine.form.FormData;

public class FormDto {

    private String key;
    private CamundaFormRef camundaFormRef;

    public void setKey(String form) {
        this.key = form;
    }

    public String getKey() {
        return key;
    }

    public CamundaFormRef getCamundaFormRef() {
        return camundaFormRef;
    }

    public void setCamundaFormRef(CamundaFormRef camundaFormRef) {
        this.camundaFormRef = camundaFormRef;
    }


    public static FormDto fromFormData(FormData formData) {
        FormDto dto = new FormDto();

        if (formData != null) {
            dto.key = formData.getFormKey();
            dto.camundaFormRef = formData.getCamundaFormRef();
        }

        return dto;
    }
}

