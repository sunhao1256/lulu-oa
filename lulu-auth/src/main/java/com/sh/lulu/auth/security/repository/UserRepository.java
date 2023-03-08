package com.sh.lulu.auth.security.repository;

import com.sh.lulu.auth.security.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> , QuerydslPredicateExecutor<User> {

   @EntityGraph(attributePaths = "roles")
   Optional<User> findOneWithRolesByUsername(String username);

   @EntityGraph(attributePaths = "roles")
   Optional<User> findOneWithRolesByEmailIgnoreCase(String email);
}
