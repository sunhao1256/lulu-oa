package com.sh.lulu.bpmn.formType;

import com.sh.lulu.auth.security.model.User;
import com.sh.lulu.auth.security.repository.UserRepository;
import com.sh.lulu.bpmn.typevalue.UserValue;
import com.sh.lulu.bpmn.valueType.UserType;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.impl.form.type.AbstractFormFieldType;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserIdFormType extends AbstractFormFieldType {
    private final UserRepository userRepository;
    private final UserType userType;


    @Override
    public String getName() {
        return "userFormType";
    }

    @Override
    public TypedValue convertToFormValue(TypedValue modelValue) {
        return modelValue;
    }

    @Override
    public TypedValue convertToModelValue(TypedValue propertyValue) {
        Object value = propertyValue.getValue();

        if (propertyValue instanceof UserValue) {
            return propertyValue;
        }
        if (value == null){
            return new UserValue(null,userType);
        }
        try {
            Optional<User> byId = userRepository.findById(propertyValue.getValue().toString());
            if (byId.isPresent()) {
                return new UserValue(byId.get(), userType);
            } else {
                throw new ProcessEngineException("user id "+value+" not found");
            }
        } catch (Exception e) {
            throw new ProcessEngineException(e);
        }
    }

    @Override
    public Object convertFormValueToModelValue(Object propertyValue) {
        return userRepository.findById(propertyValue.toString())
                .orElse(null);
    }

    @Override
    public String convertModelValueToFormValue(Object modelValue) {
        return Optional.ofNullable(modelValue).map(i -> {
            User u = (User) modelValue;
            return u.getId();
        }).orElse(null);
    }
}
