package com.sh.lulu;

import com.sh.lulu.api.ApiServer;
import com.sh.lulu.auth.security.model.QRole;
import com.sh.lulu.auth.security.model.Role;
import com.sh.lulu.auth.security.model.User;
import com.sh.lulu.auth.security.repository.RoleRepository;
import com.sh.lulu.auth.security.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest(classes = ApiServer.class)
public class AuthTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Test
    public void QueryUser() {
        String username = "admin";
        Optional<User> user = userRepository.findOneWithRolesByUsername(username);
        User user1 = user.get();
        Assertions.assertNotNull(user1);

    }

    @Test
    public void QueryRole() {
        String role = "admin";
        Optional<Role> one = roleRepository.findOne(QRole.role.name.eq(role));
        Role role1 = one.get();
        Assertions.assertNotNull(role1);
    }
}
