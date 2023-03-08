package com.sh.lulu.auth.security.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.lulu.auth.security.SecurityUtils;
import com.sh.lulu.auth.security.model.QUser;
import com.sh.lulu.auth.security.model.User;
import com.sh.lulu.auth.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

   private final UserRepository userRepository;
   private final JPAQueryFactory jpaQueryFactory;

   @Transactional(readOnly = true)
   public Optional<User> getUserWithAuthorities() {
      return Optional.of(SecurityUtils.getCurrentUsername()).flatMap(userRepository::findOneWithRolesByUsername);
   }

   @Transactional
   public void updateLastSignIn(String userName) {
      jpaQueryFactory.update(QUser.user)
              .where(QUser.user.username.eq(userName))
              .set(QUser.user.lastSignIn, new Date())
              .execute();
   }

}
