package com.sh.lulu.auth.security.service;

import com.sh.lulu.auth.security.SecurityUtils;
import com.sh.lulu.auth.security.model.User;
import com.sh.lulu.auth.security.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

   private final UserRepository userRepository;

   public UserService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Transactional(readOnly = true)
   public Optional<User> getUserWithAuthorities() {
      return Optional.of(SecurityUtils.getCurrentUsername()).flatMap(userRepository::findOneWithAuthoritiesByUsername);
   }

}
