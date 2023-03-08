package com.sh.lulu.auth.security.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender{
    MALE("male", "1"),
    FEMALE("female", "2"),
    UNKNOWN("unknown", "0");


    @JsonValue
    private final String value;
    private final String code;
}
