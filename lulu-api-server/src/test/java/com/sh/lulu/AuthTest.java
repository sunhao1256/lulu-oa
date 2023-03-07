package com.sh.lulu;

import com.sh.lulu.api.ApiServer;
import com.sh.lulu.auth.security.model.Role;
import com.sh.lulu.auth.security.model.User;
import com.sh.lulu.auth.security.repository.RoleRepository;
import com.sh.lulu.auth.security.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SpringBootTest(classes = ApiServer.class)
public class AuthTest {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void authenticate() {
        String creator = "test";
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(creator, ""));

        roleRepository.deleteByCreateByIs(creator);
        userRepository.deleteByCreateByIs(creator);
    }

    @Test
    public void CreateRole() {
        Role role = Role.builder().name("admin").build();
        Role save = roleRepository.save(role);
        Assertions.assertNotNull(save);
    }


    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void initialize() {

        Role adminRole = Role.builder().name("admin").build();
        Role userRole = Role.builder().name("user").build();
        roleRepository.saveAll(Arrays.asList(adminRole, userRole));
        String pass = passwordEncoder.encode("123456");

        User admin = User.builder()
                .username("admin")
                .firstname("frank")
                .password(pass)
                .lastname("silva")
                .email("franksilva@gmail.com")
                .contactDigit("0517-82312806")
                .activated(true)
                .roles(Set.of(adminRole))
                .build();

        User user = User.builder()
                .username("user")
                .firstname("lu")
                .password(pass)
                .lastname("lu")
                .email("lulu@gmail.com")
                .contactDigit("+86 18601487808")
                .activated(true)
                .roles(Set.of(userRole))
                .build();

        userRepository.saveAll(Arrays.asList(admin, user));
    }
}
