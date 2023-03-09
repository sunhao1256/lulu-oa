package com.sh.lulu.auth.security.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.sh.lulu.common.modeConvertor.EnumValue;
import com.sh.lulu.common.modeConvertor.EnumValueConvertor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus implements EnumValue<String> {
    ACTIVE("active", "1"),
    DISABLED("disabled", "2"),
    DELETED("deleted", "3"),
    ;

    private final String value;
    private final String code;


    public static class UserStatusConverted extends EnumValueConvertor<UserStatus, String> {

        public UserStatusConverted() {
            super(UserStatus.class);
        }
    }
}
