package com.sh.lulu.bpmn.valueType;

import org.camunda.bpm.engine.variable.impl.type.PrimitiveValueTypeImpl;
import org.springframework.stereotype.Component;

@Component
public class UserType extends PrimitiveValueTypeImpl.StringTypeImpl {
    private final static String USER_TYPE = "user";

    @Override
    public String getName() {
        return USER_TYPE;
    }
}
