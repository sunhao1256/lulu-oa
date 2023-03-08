package com.sh.lulu.common.modeCoverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;

public abstract class Object2Json implements AttributeConverter<Object,String> {
    protected final ObjectMapper objectMapper=new ObjectMapper();
    @Override
    public String convertToDatabaseColumn(Object attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
