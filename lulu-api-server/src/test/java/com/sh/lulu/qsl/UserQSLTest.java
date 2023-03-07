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

}
