package com.sh.lulu;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.lulu.api.ApiServer;
import com.sh.lulu.auth.security.model.*;
import com.sh.lulu.auth.security.model.enums.Gender;
import com.sh.lulu.auth.security.model.enums.UserStatus;
import com.sh.lulu.auth.security.repository.MenuRepository;
import com.sh.lulu.auth.security.repository.RoleRepository;
import com.sh.lulu.auth.security.repository.UserRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest(classes = ApiServer.class)
public class AuthInitial {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JPAQueryFactory jpaQueryFactory;
    @Autowired
    MenuRepository menuRepository;

    @BeforeEach
    public void authenticate() {
        String creator = "test";
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(creator, ""));
        Set<String> ids = jpaQueryFactory.selectFrom(QUser.user)
                .where(QUser.user.createBy.eq(creator))
                .fetch()
                .stream().map(User::getId).collect(Collectors.toSet());
        userRepository.deleteAllById(ids);
        roleRepository.deleteAllById(jpaQueryFactory.selectFrom(QRole.role)
                .where(QRole.role.createBy.eq(creator))
                .fetch()
                .stream().map(Role::getId).collect(Collectors.toSet()));
        menuRepository.deleteAllById(jpaQueryFactory.selectFrom(QMenu.menu)
                .where(QMenu.menu.createBy.eq(creator))
                .fetch()
                .stream().map(Menu::getId).collect(Collectors.toSet()));
    }


    @Test
    public void DeleteUser() {
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
    public void initialize() throws ParseException {

        Menu dashboard = Menu.builder().name("dashboard")
                .path("/dashboard")
                .component("basic")
                .meta(Menu.Meta.builder()
                        .title("menu.dashboard")
                        .order(1)
                        .requiresAuth(true)
                        .icon("mdi-view-dashboard-outline")
                        .build())
                .build();
        menuRepository.saveAll(Arrays.asList(dashboard));

        Menu dashboardAnalyse = Menu.builder().name("dashboard_analytics")
                .path("/dashboard/analytics")
                .component("self")
                .parent(dashboard.getId())
                .meta(Menu.Meta.builder()
                        .title("menu.dashboard")
                        .requiresAuth(true)
                        .icon("mdi-view-dashboard-outline")
                        .build())
                .build();

        menuRepository.saveAll(Arrays.asList(dashboardAnalyse));

        Role adminRole = Role.builder().name("admin").build();

        adminRole.setMenus(Set.of(dashboard, dashboardAnalyse));

        Role userRole = Role.builder().name("user").build();

        userRole.setMenus(Set.of(dashboard));

        roleRepository.saveAll(Arrays.asList(adminRole, userRole));


        String pass = passwordEncoder.encode("123456");

        User admin = User.builder()
                .username("admin")
                .firstname("frank")
                .password(pass)
                .lastname("silva")
                .email("franksilva@gmail.com")
                .contactDigit("0517-82312806")
                .gender(Gender.MALE)
                .birth(DateUtils.parseDate("1996-12-16", "yyyy-MM-DD"))
                .status(UserStatus.ACTIVE)
                .roles(Set.of(adminRole))
                .build();

        User user = User.builder()
                .username("user")
                .firstname("lu")
                .password(pass)
                .lastname("lu")
                .email("lulu@gmail.com")
                .gender(Gender.FEMALE)
                .contactDigit("+86 18601487808")
                .status(UserStatus.DISABLED)
                .roles(Set.of(userRole))
                .build();

        userRepository.saveAll(Arrays.asList(admin, user));
    }


}
