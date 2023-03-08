package com.sh.lulu.auth.security.repository;


import com.sh.lulu.auth.security.model.Menu;
import com.sh.lulu.auth.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MenuRepository extends JpaRepository<Menu, String> , QuerydslPredicateExecutor<Menu> {
}
