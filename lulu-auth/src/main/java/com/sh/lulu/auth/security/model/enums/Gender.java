package com.sh.lulu.auth.security.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.sh.lulu.common.modeConvertor.EnumValue;
import com.sh.lulu.common.modeConvertor.EnumValueConvertor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender implements EnumValue<String> {
    MALE("male", "1"),
    FEMALE("female", "2"),
    UNKNOWN("unknown", "0");

    private final String value;
    private final String code;


    public static class GenderConverter extends EnumValueConvertor<Gender,String>{

        public GenderConverter() {
            super(Gender.class);
        }
    }
}
