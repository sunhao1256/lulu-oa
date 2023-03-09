package com.sh.lulu.auth.security.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sh.lulu.common.model.Base;
import com.sh.lulu.common.modeCoverter.Object2Json;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "MENU")
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends Base {

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Column(name = "PATH", unique = true, nullable = false)
    private String path;

    @Column(name = "COMPONENT", nullable = false)
    private String component;

    @Column(name = "PARENT")
    private String parent;

    @Column(name = "META", nullable = false)
    @Convert(converter = MetaConverter.class)
    private Meta meta;

    @Transient
    private Set<Menu> children;


    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static public class Meta {
        private String title;
        private Boolean requiresAuth;
        private Boolean hide;
        private Integer order;
        private String icon;
    }

    static public class MetaConverter extends Object2Json {

        @Override
        public Object convertToEntityAttribute(String dbData) {
            try {
                return objectMapper.readValue(dbData, Meta.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
