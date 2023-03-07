package com.sh.lulu.auth.security.repository;


import com.sh.lulu.auth.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

public interface RoleRepository extends JpaRepository<Role, String> , QuerydslPredicateExecutor<Role> {
    @Transactional
    int deleteByCreateByIs(String creator);
}
