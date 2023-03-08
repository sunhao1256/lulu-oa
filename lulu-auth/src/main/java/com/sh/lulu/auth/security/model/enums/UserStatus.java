package com.sh.lulu.auth.security.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    ACTIVE("active", "1"),
    DISABLED("disabled", "2"),
    DELETED("deleted", "3"),
    ;

    @JsonValue
    private final String value;
    private final String code;
}
