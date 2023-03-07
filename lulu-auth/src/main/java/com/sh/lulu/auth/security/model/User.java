package com.sh.lulu.auth.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sh.lulu.auth.model.Base;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "USER")
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends Base {

    @Column(name = "USERNAME", length = 50, unique = true)
    @NotNull
    @Size(min = 1, max = 50)
    private String username;

    @JsonIgnore
    @Column(name = "PASSWORD", length = 100)
    @NotNull
    @Size(min = 1, max = 100)
    private String password;

    @Column(name = "FIRSTNAME", length = 50)
    @NotNull
    @Size(min = 1, max = 50)
    private String firstname;

    @Column(name = "LASTNAME", length = 50)
    @NotNull
    @Size(min = 1, max = 50)
    private String lastname;

    @Column(name = "EMAIL", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String email;

    @Column(name = "CONTACT_DIGIT", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String contactDigit;

    @Column(name = "ACTIVATED")
    @NotNull
    private boolean activated;

    @ManyToMany
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID", table = "USER")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", table = "ROLE")})
    @BatchSize(size = 20)
    private Set<Role> roles;

}
