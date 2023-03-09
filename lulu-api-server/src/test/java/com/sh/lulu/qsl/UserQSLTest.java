package com.sh.lulu.qsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.lulu.api.ApiServer;
import com.sh.lulu.auth.security.model.QUser;
import com.sh.lulu.auth.security.model.User;
import com.sh.lulu.auth.security.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest(classes = ApiServer.class)
public class UserQSLTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JPAQueryFactory jpaQueryFactory;


    @Test
    public void QueryByName() {
        Optional<User> hello = userRepository.findOne(QUser.user.username.eq("admin"));
        Assertions.assertTrue(hello.isPresent());
    }

    @Test
    public void QueryByContactDigit() {
        User hello = jpaQueryFactory.selectFrom(QUser.user)
                .where(
                        QUser.user.contactDigit.eq("hello")
                ).fetchOne();
        Assertions.assertNull(hello);
    }

    @Test
    public void lazyRetchRole(){
        String username = "admin";
        Optional<User> one = userRepository.findOne(QUser.user.username.eq(username));
        User user = one.get();
        Assertions.assertNotNull(user);
    }


    @Test
    public void relationShipQueryWithDSL(){
        String username  = "admin";
        User user = jpaQueryFactory
                .selectFrom(QUser.user)
                .leftJoin(QUser.user.roles)
                .where(QUser.user.username.eq(username))
                .fetchOne();
        Assertions.assertNotNull(user);
    }
    @Test
    public void relationShipQueryWithEntityGraph(){
        String username  = "admin";
        User user = userRepository.findOneWithRolesByUsername(username).get();
        Assertions.assertNotNull(user);
    }
}
