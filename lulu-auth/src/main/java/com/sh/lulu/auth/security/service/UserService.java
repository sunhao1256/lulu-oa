package com.sh.lulu.auth.security.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.lulu.auth.security.SecurityUtils;
import com.sh.lulu.auth.security.model.Menu;
import com.sh.lulu.auth.security.model.QUser;
import com.sh.lulu.auth.security.model.User;
import com.sh.lulu.auth.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public Optional<User> getUserWithAuthorities() {
        return Optional.of(SecurityUtils.getCurrentUsername())
                .flatMap(userRepository::findOneWithRolesByUsername)
                .map(this::generateMenuTree);
    }


    private User generateMenuTree(User user) {
        Set<Menu> candidates = user.getRoles()
                .stream()
                .flatMap(role -> role.getMenus().stream())
                .collect(Collectors.toSet());
        candidates.stream().
                filter(menu -> ObjectUtils.isEmpty(menu.getParent()))
                .forEach(p -> cascade(p, candidates));
        user.setMenus(candidates.stream().
                filter(menu -> ObjectUtils.isEmpty(menu.getParent()))
                .map(p -> cascade(p, candidates))
                .collect(Collectors.toSet()));
        return user;
    }

    private Menu cascade(Menu parent, Set<Menu> candidates) {
        parent.setChildren(candidates.stream()
                .filter(candidate -> !ObjectUtils.isEmpty(candidate.getParent()))
                .filter(candidate ->
                        candidate.getParent().equals(parent.getId()))
                .collect(Collectors.toSet())
        );
        return parent;
    }

    @Transactional
    public void updateLastSignIn(String userName) {
        jpaQueryFactory.update(QUser.user)
                .where(QUser.user.username.eq(userName))
                .set(QUser.user.lastSignIn, new Date())
                .execute();
    }

}
