package com.sh.lulu.auth.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private String value;
    @JsonIgnore
    private Long expire;
    private Date expireDate;
    private String username;
    private String type;

}
