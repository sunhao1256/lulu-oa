package com.sh.lulu.auth.security.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sh.lulu.common.model.Base;
import com.sh.lulu.auth.security.model.enums.Gender;
import com.sh.lulu.auth.security.model.enums.UserStatus;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "USER")
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends Base {

    @Column(name = "USERNAME", length = 50, unique = true, nullable = false)
    @NotNull
    @Size(min = 1, max = 50)
    private String username;

    @JsonIgnore
    @Column(name = "PASSWORD", length = 100, nullable = false)
    @Size(min = 1, max = 100)
    private String password;

    @Column(name = "FIRSTNAME", length = 50)
    @Size(min = 1, max = 50)
    private String firstname;

    @Column(name = "LASTNAME", length = 50)
    @Size(min = 1, max = 50)
    private String lastname;

    @Column(name = "EMAIL", length = 50, nullable = false)
    @Size(min = 4, max = 50)
    private String email;

    @Column(name = "CONTACT_DIGIT", length = 50)
    @Size(min = 4, max = 50)
    private String contactDigit;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "GENDER", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Gender gender = Gender.UNKNOWN;

    @Column(name = "BIRTH")
    @Temporal(TemporalType.DATE)
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-DD")
    private Date birth;

    @Column(name = "LAST_SIGN_IN")
    private Date lastSignIn;

    @ManyToMany
    // avoid fetch role and menu when query userList
    @JsonIgnore
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID", table = "USER")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", table = "ROLE")})
    @BatchSize(size = 20)
    private Set<Role> roles;


    @Transient
    private Set<Menu> menus;

}
