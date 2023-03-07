package com.sh.lulu.bpmn.typevalue;

import com.sh.lulu.auth.security.model.User;
import org.camunda.bpm.engine.variable.impl.value.AbstractTypedValue;
import org.camunda.bpm.engine.variable.type.ValueType;

public class UserValue extends AbstractTypedValue<User> {
    public UserValue(User value, ValueType type) {
        super(value, type);
    }
}
