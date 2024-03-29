package com.sh.lulu.auth.security.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sh.lulu.common.model.Base;
import com.sh.lulu.auth.security.model.enums.Gender;
import com.sh.lulu.auth.security.model.enums.UserStatus;
import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "USER")
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor

@NamedEntityGraph(
        name = "roles-menus",
        attributeNodes = @NamedAttributeNode(value = "roles", subgraph = "menus"),
        subgraphs = @NamedSubgraph(
                name = "menus", attributeNodes = @NamedAttributeNode(value = "menus"))

)
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
    @Convert(converter = UserStatus.UserStatusConverted.class)
    private UserStatus status;

    @Column(name = "GENDER", nullable = false)
    @Builder.Default
    @Convert(converter = Gender.GenderConverter.class)
    private Gender gender = Gender.UNKNOWN;

    @Column(name = "BIRTH")
    @Temporal(TemporalType.DATE)
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-DD")
    private Date birth;

    @Column(name = "LAST_SIGN_IN")
    private Date lastSignIn;

    @ManyToMany
    @JoinTable(
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT),
            inverseForeignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT),
            name = "USER_ROLE",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID", table = "USER")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", table = "ROLE")})
    @BatchSize(size = 20)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Role> roles;


    @Transient
    private Set<Menu> menus;

}
