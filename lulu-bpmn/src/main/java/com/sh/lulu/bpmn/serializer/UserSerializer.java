package com.sh.lulu.bpmn.serializer;

import com.sh.lulu.auth.security.repository.UserRepository;
import com.sh.lulu.bpmn.typevalue.UserValue;
import org.camunda.bpm.engine.impl.variable.serializer.AbstractTypedValueSerializer;
import org.camunda.bpm.engine.impl.variable.serializer.ValueFields;
import org.camunda.bpm.engine.variable.impl.value.UntypedValueImpl;
import org.camunda.bpm.engine.variable.type.ValueType;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class UserSerializer extends AbstractTypedValueSerializer<UserValue> {
    private final UserRepository userRepository;

    public UserSerializer(ValueType type, UserRepository userRepository) {
        super(type);
        this.userRepository = userRepository;
    }

    @Override
    protected boolean canWriteValue(TypedValue value) {
        return false;
    }

    @Override
    public String getName() {
        return "user";
    }

    @Override
    public void writeValue(UserValue value, ValueFields valueFields) {
        valueFields.setTextValue(value.getValue().getId());
    }

    @Override
    public UserValue readValue(ValueFields valueFields, boolean deserializeValue, boolean isTransient) {
        String id = valueFields.getTextValue();
        if (ObjectUtils.isEmpty(id)) {
            return new UserValue(null, valueType);
        }
        return new UserValue(userRepository.findById(id).get(), valueType);
    }

    @Override
    public UserValue convertToTypedValue(UntypedValueImpl untypedValue) {
        return null;
    }
}
