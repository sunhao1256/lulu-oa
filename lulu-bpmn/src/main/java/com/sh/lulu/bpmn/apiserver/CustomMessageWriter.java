package com.sh.lulu.bpmn.apiserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.lulu.common.response.CommonResult;

import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Produces("application/json")
public class CustomMessageWriter implements MessageBodyWriter<Object> {
    private final ObjectMapper objectMapper;

    @Inject
    public CustomMessageWriter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean isWriteable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return mediaType.equals(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public void writeTo(Object o, Class type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {

        Object result = o;
        if (!CommonResult.class.isAssignableFrom(type)) {
            result = CommonResult.success(o);
        }
        objectMapper.writer().writeValue(entityStream, result);
    }
}
