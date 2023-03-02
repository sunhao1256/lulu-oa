package com.sh.lulu.auth.security.repository;


import com.sh.lulu.auth.security.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
