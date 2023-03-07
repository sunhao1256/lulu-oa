package com.sh.lulu.bpmn.config;

import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.form.type.AbstractFormFieldType;
import org.camunda.bpm.engine.impl.variable.serializer.AbstractTypedValueSerializer;
import org.camunda.bpm.engine.impl.variable.serializer.TypedValueSerializer;
import org.camunda.bpm.engine.variable.impl.type.AbstractValueTypeImpl;
import org.camunda.bpm.spring.boot.starter.configuration.Ordering;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(Ordering.DEFAULT_ORDER + 1)
@AllArgsConstructor
public class CustomConfiguration implements ProcessEnginePlugin {
    private final List<AbstractFormFieldType> customFormTypes;
    private final List<AbstractValueTypeImpl> customValueTypes;
    private final List<TypedValueSerializer> customTypeValueSerializer;

    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        processEngineConfiguration.setCustomFormTypes(customFormTypes);
        processEngineConfiguration.setCustomPreVariableSerializers(customTypeValueSerializer);
    }

    @Override
    public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        for (AbstractValueTypeImpl customValueType : customValueTypes) {
            processEngineConfiguration.getValueTypeResolver().addType(customValueType);
        }
    }

    @Override
    public void postProcessEngineBuild(ProcessEngine processEngine) {

    }


}

