package com.sh.lulu.auth.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sh.lulu.common.model.Base;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "ROLE")
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor

public class Role extends Base {

    @Column(name = "NAME", length = 50, unique = true, nullable = false)
    @NotNull
    private String name;

    @ManyToMany
    @JoinTable(
            name = "ROLE_MENU",
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT),
            inverseForeignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT),
            joinColumns = {@JoinColumn(name = "MENU_ID", referencedColumnName = "ID", table = "MENU")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", table = "ROLE")})
    @BatchSize(size = 40)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Set<Menu> menus;
}
