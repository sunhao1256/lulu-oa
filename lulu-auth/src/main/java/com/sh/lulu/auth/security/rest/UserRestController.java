package com.sh.lulu.auth.security.rest;

import com.sh.lulu.auth.security.SecurityUtils;
import com.sh.lulu.auth.security.model.Token;
import com.sh.lulu.auth.security.model.User;
import com.sh.lulu.auth.security.repository.TokenRepository;
import com.sh.lulu.auth.security.repository.UserRepository;
import com.sh.lulu.auth.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/user")
    public User getActualUser() {
        return userService.getUserWithAuthorities().get();
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
