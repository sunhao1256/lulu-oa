package com.sh.lulu.bpmn.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.lulu.auth.security.repository.UserRepository;
import com.sh.lulu.bpmn.typevalue.UserValue;
import com.sh.lulu.bpmn.valueType.UserType;
import org.camunda.bpm.engine.impl.variable.serializer.AbstractTypedValueSerializer;
import org.camunda.bpm.engine.impl.variable.serializer.ValueFields;
import org.camunda.bpm.engine.variable.impl.value.UntypedValueImpl;
import org.camunda.bpm.engine.variable.type.ValueType;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.springframework.stereotype.Component;

@Component
public class UserSerializer extends AbstractTypedValueSerializer<UserValue> {
    private final UserRepository userRepository;

    public UserSerializer(ValueType type, UserRepository userRepository) {
        super(type);
        this.userRepository = userRepository;
    }

    @Override
    protected boolean canWriteValue(TypedValue value) {
        return true;
    }

    @Override
    public String getName() {
        return "user";
    }

    @Override
    public void writeValue(UserValue value, ValueFields valueFields) {
        valueFields.setLongValue(value.getValue().getId());
    }

    @Override
    public UserValue readValue(ValueFields valueFields, boolean deserializeValue, boolean isTransient) {
        Long longValue = valueFields.getLongValue();
        return new UserValue(userRepository.findById(longValue).get(),valueType);
    }

    @Override
    public UserValue convertToTypedValue(UntypedValueImpl untypedValue) {
        return null;
    }
}
