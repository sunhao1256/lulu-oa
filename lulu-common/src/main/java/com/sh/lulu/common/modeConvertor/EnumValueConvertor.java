package com.sh.lulu.common.modeConvertor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;

public abstract class EnumValueConvertor<T extends Enum<T> & EnumValue<E>, E> implements AttributeConverter<T, E> {
    private final Class<T> clazz;

    public EnumValueConvertor(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public E convertToDatabaseColumn(T attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public T convertToEntityAttribute(E dbData) {
        T[] enums = clazz.getEnumConstants();
        for (T e : enums) {
            if (e.getCode().equals(dbData)) {
                return e;
            }
        }
        throw new UnsupportedOperationException();
    }
}
