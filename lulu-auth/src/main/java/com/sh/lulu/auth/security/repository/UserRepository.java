package com.sh.lulu.auth.security.repository;

import com.sh.lulu.auth.security.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>, QuerydslPredicateExecutor<User> {

    @EntityGraph(value = "roles-menus")
    Optional<User> findOneWithRolesByUsername(String username);

    @EntityGraph(value = "roles-menus")
    Optional<User> findOneWithRolesByEmailIgnoreCase(String email);
}
